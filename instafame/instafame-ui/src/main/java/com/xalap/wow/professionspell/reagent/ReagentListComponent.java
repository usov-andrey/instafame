package com.xalap.wow.professionspell.reagent;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.RouterLink;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.auction.AuctionDataOld;
import com.xalap.wow.auction.AuctionService;
import com.xalap.wow.item.ItemFrame;
import com.xalap.wow.ui.PriceComponent;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 29/11/2019
 */
public class ReagentListComponent extends CustomVerticalLayout {

    public ReagentListComponent(AuctionDataOld auctionData, ReceiptReagentService receiptReagentService, ItemCraftCostBean bean) {
        this(auctionData, receiptReagentService, bean.getReagents(), bean.getReagentAuctionList());
    }

    public ReagentListComponent(AuctionDataOld auctionData, AuctionService auctionService, ReceiptReagentService receiptReagentService,
                                List<ReceiptReagentBean> reagentBeanList) {
        this(auctionData, receiptReagentService, reagentBeanList,
                auctionService.getReagentsAuctions(auctionData, reagentBeanList));
    }

    public ReagentListComponent(AuctionDataOld auctionData, ReceiptReagentService receiptReagentService, List<ReceiptReagentBean> reagentBeanList,
                                List<Auction> reagentAuctionList) {
        long cost = 0;
        for (ReceiptReagentBean receiptReagentBean : reagentBeanList) {
            add(new RouterLink(receiptReagentBean.getItem().getName() + " x" + receiptReagentBean.getQuantity(),
                    ItemFrame.class, receiptReagentBean.getItem().getId()));
            long price = receiptReagentService.getPrice(auctionData, receiptReagentBean, reagentAuctionList);
            if (price > 0) {
                add(new PriceComponent(price));
            } else {
                add(new Label("Нет на аукционе"));
            }
            cost = price + cost;
        }
        add(new Label("Всего:"));
        add(new PriceComponent(cost));
    }
}
