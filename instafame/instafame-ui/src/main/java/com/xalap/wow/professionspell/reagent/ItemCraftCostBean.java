package com.xalap.wow.professionspell.reagent;

import com.xalap.wow.api.auction.Auction;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 21/12/2019
 */
public class ItemCraftCostBean {

    private long reagentPrice;
    private List<ReceiptReagentBean> reagents;
    private List<Auction> reagentAuctionList;

    public long getReagentPrice() {
        return reagentPrice;
    }

    public void setReagentPrice(long reagentPrice) {
        this.reagentPrice = reagentPrice;
    }

    public List<Auction> getReagentAuctionList() {
        return reagentAuctionList;
    }

    public void setReagentAuctions(List<Auction> reagentAuctionList) {
        this.reagentAuctionList = reagentAuctionList;
    }

    public List<ReceiptReagentBean> getReagents() {
        return reagents;
    }

    public void setReagents(List<ReceiptReagentBean> reagents) {
        this.reagents = reagents;
    }

}
