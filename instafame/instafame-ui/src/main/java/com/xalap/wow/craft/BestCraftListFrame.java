package com.xalap.wow.craft;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.component.ClipboardHelper;
import com.xalap.vaadin.custom.component.fluent.Anchor;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.custom.data.DataChangeListener;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import com.xalap.wow.api.Region;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.auction.AuctionDataOld;
import com.xalap.wow.auction.AuctionService;
import com.xalap.wow.auction.ItemRegionSaleBean;
import com.xalap.wow.auction.ItemRegionSaleRepository;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.item.ItemFrame;
import com.xalap.wow.item.stats.ItemStatsBean;
import com.xalap.wow.item.stats.ItemStatsService;
import com.xalap.wow.professionspell.SpellBean;
import com.xalap.wow.professionspell.SpellService;
import com.xalap.wow.professionspell.reagent.ItemCraftCostBean;
import com.xalap.wow.professionspell.reagent.ReagentListComponent;
import com.xalap.wow.professionspell.reagent.ReceiptReagentBean;
import com.xalap.wow.professionspell.reagent.ReceiptReagentService;
import com.xalap.wow.tsm.TSMService;
import com.xalap.wow.ui.PriceComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Что лучше сейчас скрафтить, чтобы заработать
 *
 * @author Усов Андрей
 * @since 08/08/2019
 */
@Route(value = BestCraftListFrame.VIEW_NAME, layout = MainLayout.class)
public class BestCraftListFrame extends RootListFrame<BestCraftGridBean> {

    public static final String VIEW_NAME = "bestCraftList";

    @Autowired
    public BestCraftListFrame(SpellService receiptService, AuctionService auctionService, TSMService tsmService,
                              ItemRegionSaleRepository itemRegionSaleRepository, ItemStatsService itemStatsService,
                              ReceiptReagentService receiptReagentService) {
        super((bClass) -> new GridPanel<>(BestCraftGridBean.class));

        for (Profession profession : Profession.values()) {
            addTab(profession.name());
        }

        gridPanel.filters().addInteger("Мин. продаж", new BiPredicate<BestCraftGridBean, Integer>() {
            @Override
            public boolean test(BestCraftGridBean bestCraftGridBean, Integer integer) {
                return bestCraftGridBean.getSalesPerDay() > integer;
            }
        });

        ProfessionFilter<BestCraftGridBean> professionFilter = new ProfessionFilter<>(gridPanel);
        //ClipboardHelper clipboardHelper = new ClipboardHelper("", new Button("В буфер"));

        AuctionDataOld auctionData = auctionService.getAuction();

        setMemoryDataProvider(() -> {
            List<BestCraftGridBean> result = new ArrayList<BestCraftGridBean>();

            Profession profession = Profession.valueOf(getSelectedTab().getLabel());
            Map<SpellBean, List<ReceiptReagentBean>> receiptBeanListMap = receiptService.getReceiptMap(profession);
            professionFilter.setFilterValue(profession, receiptBeanListMap.keySet(), BestCraftGridBean::getReceiptBean);

            for (Map.Entry<SpellBean, List<ReceiptReagentBean>> receiptBeanListEntry : receiptBeanListMap.entrySet()) {
                SpellBean receiptBean = receiptBeanListEntry.getKey();
                //Если у рецепта есть ранк, то смотрим только последний
                if (receiptBean.isRank1Or2()) {
                    continue;
                }

                List<ReceiptReagentBean> reagentBeanList = receiptBeanListEntry.getValue();
                List<Auction> reagentsAuctions = auctionService.getReagentsAuctions(auctionData, reagentBeanList);
                ItemBean item = receiptBean.getItem();

                Optional<Auction> minBuyout = auctionData.getMinBuyout(item);
                if (minBuyout.isPresent()) {
                    Auction auction = minBuyout.get();
                    BestCraftGridBean gridBean = new BestCraftGridBean();
                    long price = receiptService.getCraftCost(auctionData, reagentBeanList, reagentsAuctions);
                    long possibleSellPrice = auction.getBuyout() / auction.getQuantity() * receiptBean.getQuantity();
                    if (possibleSellPrice > price) {
                        gridBean.setItemBean(item);
                        gridBean.setAuctionsCount(auctionData.getAuctions(item).size());
                        gridBean.setReceiptBean(receiptBean);
                        gridBean.setProfit(possibleSellPrice - price);
                        ItemCraftCostBean itemCraftCostBean = new ItemCraftCostBean();
                        itemCraftCostBean.setReagentPrice(price);
                        itemCraftCostBean.setReagentAuctions(reagentsAuctions);
                        itemCraftCostBean.setReagents(reagentBeanList);
                        gridBean.setCraftCostBean(itemCraftCostBean);
                        gridBean.setSellPrice(possibleSellPrice);
                        result.add(gridBean);
                    }
                }
            }

            CollectionHelper.fillFK(result, BestCraftGridBean::getItemBean,
                    itemRegionSaleRepository::findByItems,
                    ItemRegionSaleBean::getItem,
                    BestCraftGridBean::setItemRegionSaleBean
            );
            //Заполняем количество продаж
            Set<ItemBean> itemBeanSet = CollectionHelper.newHashSet(result, BestCraftGridBean::getItemBean);
            Map<ItemBean, List<ItemStatsBean>> itemStatsMap = itemStatsService.getItemStatsMap(itemBeanSet);
            for (BestCraftGridBean bestCraftGridBean : result) {
                List<ItemStatsBean> itemStatsBeen = itemStatsMap.get(bestCraftGridBean.getItemBean());
                bestCraftGridBean.setSalesPerDay(itemStatsBeen == null ? 0d : itemStatsService.avgSoldByDay(itemStatsBeen));
            }

            return result;
        });
        gridPanel.dataSource().addListener(new DataChangeListener<Collection<BestCraftGridBean>>() {
            @Override
            public void onDataChange(Collection<BestCraftGridBean> value) {
                System.out.println(
                        StringHelper.join(value, new Function<BestCraftGridBean, String>() {
                            @Override
                            public String apply(BestCraftGridBean bestCraftGridBean) {
                                return "i:" + bestCraftGridBean.getItemBean().getItemId();
                            }
                        }, ","));
            }
        });

        gridPanel.buttons()
                //.addWithReload("Обновить аукцион", auctionService::updateAuction)
                .addWithReload("Обновить цены на рецепты", () -> {
                    Set<ItemBean> items = CollectionHelper.newHashSet(gridPanel.getItems(),
                            BestCraftGridBean::getItemBean);
                    itemStatsService.recount(items);
                })
                .addWithReload("Обновить цены на ингридиенты", () -> {
                    Set<ItemBean> items = new HashSet<ItemBean>();
                    for (BestCraftGridBean bestCraftGridBean : gridPanel.getItems()) {
                        items.addAll(bestCraftGridBean.getCraftCostBean().getReagents().stream().map(ReceiptReagentBean::getItem).collect(Collectors.toList()));
                    }
                    itemStatsService.recount(items);
                });

        gridPanel.dataSource().setSortComparator((SerializableComparator<BestCraftGridBean>) (o1, o2) -> o2.getProfit().compareTo(o1.getProfit()));

        GridColumns<BestCraftGridBean> columns = gridPanel.columns();
        columns.addComponent("Предмет", bean -> {
            CustomVerticalLayout layout = new CustomVerticalLayout();
            //Ссылка на предмет
            layout.add(new RouterLink(bean.getItemBean().getName() + " x" + bean.getReceiptBean().getQuantity(),
                    ItemFrame.class, bean.getItemBean().getId()));
            layout.add(new Label(" "));
            layout.add(new Anchor("https://www.tradeskillmaster.com/items/" + bean.getItemBean().getItemId(), "TSM").blank());
            layout.add(new Anchor("https://ru.wowhead.com/item=" + bean.getItemBean().getItemId(), "WowHead").blank());
            Label label = new Label(bean.getReceiptBean().skill());
            layout.add(label);
            if (!bean.isAllReagentsInAuction()) {
                UIUtils.setTextColor(TextColor.ERROR, label);
            }

            return layout;
        });
        columns.add("Продаж в день", bean -> StringHelper.toString(bean.getSalesPerDay()));
        columns.add("TSM:Продаж в день", bean -> bean.getItemRegionSaleBean() != null ? "" + bean.getItemRegionSaleBean().getRegionAvgDailySold() : "");
        columns.add("TSM:Вероятность продажи", bean -> bean.getItemRegionSaleBean() != null ? "" + bean.getItemRegionSaleBean().getRegionSaleRate() * 100 : "");
        columns.add("Аукционов", bean -> bean.getAuctionsCount() +
                (bean.getItemRegionSaleBean() != null ? "/" + bean.getItemRegionSaleBean().getRegionQuantity() : "")
        );
        columns.addComponent("Прибыль", bestCraftGridBean -> new PriceComponent(bestCraftGridBean.getProfit()))
                .setComparator((o1, o2) -> {
                    return o1.getProfit().compareTo(o2.getProfit());
                });

        columns.addComponent("Цена продажи", bestCraftGridBean -> new PriceComponent(bestCraftGridBean.getSellPrice()));
        columns.add("Процент от средней цены", bestCraftGridBean -> {
            return bestCraftGridBean.getItemRegionSaleBean() != null ?
                    StringHelper.toString((double) bestCraftGridBean.getSellPrice() / bestCraftGridBean.getReceiptBean().getQuantity() * 100 / bestCraftGridBean.getItemRegionSaleBean().getMarketValue()) :
                    "";
        });
        columns.addComponent("Цена реагентов", bestCraftGridBean -> new PriceComponent(bestCraftGridBean.getCraftCostBean().getReagentPrice()));
        columns.addComponent("Реагенты", bean -> {
            return new ReagentListComponent(auctionData, receiptReagentService, bean.getCraftCostBean());
        });

        columns.actions((bestCraftGridBeanGridActions, bean) -> {
            ClipboardHelper clipboardHelper2 = new ClipboardHelper(bean.getItemBean().getName(), new Button("В буфер"));
            bestCraftGridBeanGridActions.add(clipboardHelper2);

            bestCraftGridBeanGridActions.addWithReload("TSM Update", () ->
                    tsmService.update(bean.getItemBean(), Region.flamegor)
            );
        });

    }

}
