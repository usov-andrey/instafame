package com.xalap.wow.item.auction;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.auction.AuctionDataOld;
import com.xalap.wow.auction.AuctionService;
import com.xalap.wow.auction.PriceHelper;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.item.ItemComponent;
import com.xalap.wow.item.ItemService;
import com.xalap.wow.item.stats.ItemStatsBean;
import com.xalap.wow.item.stats.ItemStatsService;
import com.xalap.wow.professionspell.SpellService;
import com.xalap.wow.professionspell.reagent.ItemCraftCostBean;
import com.xalap.wow.professionspell.reagent.ReagentListComponent;
import com.xalap.wow.professionspell.reagent.ReceiptReagentBean;
import com.xalap.wow.professionspell.reagent.ReceiptReagentService;
import com.xalap.wow.ui.PriceComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
@Route(value = AuctionItemListFrame.VIEW_NAME, layout = MainLayout.class)
public class AuctionItemListFrame extends RootListFrame<AuctionItemGridBean> {
    public static final String VIEW_NAME = "aucItemList";

    @Autowired
    public AuctionItemListFrame(ItemService service, ItemStatsService statsService, AuctionService auctionService,
                                SpellService spellService, ReceiptReagentService receiptReagentService) {
        super((bClass) -> new GridPanel<>(AuctionItemGridBean.class));

        AuctionDataOld auctionData = auctionService.getAuction();

        setMemoryDataProvider(() -> {
            List<AuctionItemGridBean> result = new ArrayList<>();
            Map<Long, List<Auction>> auctionDataItems = auctionData.getItems();
            for (Long itemId : auctionDataItems.keySet()) {
                List<Auction> auctions = auctionDataItems.get(itemId);
                Optional<Auction> minBuyout = auctionData.getMinBuyout(auctions);
                if (minBuyout.isPresent()) {
                    Long buyout = minBuyout.get().getBuyout();
                    Integer quantity = minBuyout.get().getQuantity();
                    if (PriceHelper.inGold(buyout / quantity) > 500) {
                        AuctionItemGridBean bean = new AuctionItemGridBean();
                        bean.setItem(service.getItem(itemId));
                        bean.setMinBuyout(buyout);
                        bean.setQuantity(quantity);
                        bean.setAucCount(auctions.size());
                        result.add(bean);
                    }
                }
            }

            Set<ItemBean> items = CollectionHelper.newHashSet(result,
                    AuctionItemGridBean::getItem);
            Map<ItemBean, List<ItemStatsBean>> itemStatsMap = statsService.getItemStatsMap(items);
            for (AuctionItemGridBean auctionItemGridBean : result) {
                List<ItemStatsBean> itemStatsBeen = itemStatsMap.get(auctionItemGridBean.getItem());
                if (itemStatsBeen != null) {
                    auctionItemGridBean.setSalesPerDay(statsService.avgSoldByDay(itemStatsBeen));
                    auctionItemGridBean.setSellPrice(statsService.avgSellPrice(itemStatsBeen));
                }
            }
            result = result.stream()
                    .filter(auctionItemGridBean -> auctionItemGridBean.getSalesPerDay() >= 1)
                    .collect(Collectors.toList());

            Map<ItemBean, List<ReceiptReagentBean>> allReceiptMap = spellService.getAllItemReagentMap();
            for (AuctionItemGridBean auctionItemGridBean : result) {
                List<ReceiptReagentBean> receiptReagentBeen = allReceiptMap.get(auctionItemGridBean.getItem());
                if (receiptReagentBeen != null) {
                    //Значит крафтится
                    List<Auction> reagentsAuctions = auctionService.getReagentsAuctions(auctionData, receiptReagentBeen);
                    ItemCraftCostBean itemCraftCostBean = new ItemCraftCostBean();
                    itemCraftCostBean.setReagentPrice(spellService.getCraftCost(auctionData, receiptReagentBeen, reagentsAuctions));
                    itemCraftCostBean.setReagentAuctions(reagentsAuctions);
                    itemCraftCostBean.setReagents(receiptReagentBeen);
                    auctionItemGridBean.setCraftCostBean(itemCraftCostBean);
                }
            }

            Collections.sort(result, (o1, o2) -> o2.getMinBuyout().compareTo(o1.getMinBuyout()));
            return result;
        });

        gridPanel.filters()
                .addText("Поиск", (bean, s) -> s.isEmpty() || bean.getItem().getName().toLowerCase().contains(s.toLowerCase()));

        GridColumns<AuctionItemGridBean> columns = gridPanel.columns();
        columns.addComponent("Название", bean -> new ItemComponent(bean.getItem()));

        columns.addComponent("Цена лота", bean -> new PriceComponent(bean.getMinBuyout()));
        columns.add("Количество в лоте", bean -> "" + bean.getQuantity());
        columns.add("На аукционе", bean -> "" + bean.getAucCount());
        columns.add("Продаж в день", bean -> StringHelper.toString(bean.getSalesPerDay()));
        columns.addComponent("Средняя цена продажи", bean -> new PriceComponent(bean.getSellPrice()));
        columns.addComponent("Цена реагентов", bestCraftGridBean -> bestCraftGridBean.getCraftCostBean() != null ?
                new PriceComponent(bestCraftGridBean.getCraftCostBean().getReagentPrice()) : new Label(""));
        columns.addComponent("Реагенты", bean -> {

            return bean.getCraftCostBean() != null ? new ReagentListComponent(auctionData, receiptReagentService, bean.getCraftCostBean()) :
                    new Label("");
        });

    }

}
