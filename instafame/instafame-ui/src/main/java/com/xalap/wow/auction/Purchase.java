package com.xalap.wow.auction;

import com.xalap.wow.tsm.TsmItem;

import java.util.List;

/**
 * Покупка на аукционе
 *
 * @author Usov Andrey
 * @since 01.06.2022
 */
public class Purchase {

    private final long profit;
    private final long investment;
    private final long quantity;
    private final List<AuctionBean> auctionBeanList;
    private final int index;
    private final TsmItem tsmItem;

    public Purchase(long profit, long investment, long quantity, int index, List<AuctionBean> buyoutAuctions, TsmItem tsmItem) {
        this.profit = profit;
        this.investment = investment;
        this.quantity = quantity;
        this.index = index;
        this.auctionBeanList = buyoutAuctions;
        this.tsmItem = tsmItem;
    }

    public long getProfit() {
        return profit;
    }

    public long getInvestment() {
        return investment;
    }

    public double roi() {
        double result = getProfit();
        return result / getInvestment();
    }

    public List<AuctionBean> getAuctionBeanList() {
        return auctionBeanList;
    }

    public int getIndex() {
        return index;
    }

    public long getQuantity() {
        return quantity;
    }

    public TsmItem getTsmItem() {
        return tsmItem;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "profit=" + profit +
                ", auctionBeanList=" + auctionBeanList +
                ", index=" + index +
                '}';
    }
}
