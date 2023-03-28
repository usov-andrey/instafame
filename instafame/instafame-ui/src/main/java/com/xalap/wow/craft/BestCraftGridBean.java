package com.xalap.wow.craft;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.wow.auction.ItemRegionSaleBean;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.item.stats.ItemStatsBean;
import com.xalap.wow.professionspell.SpellBean;
import com.xalap.wow.professionspell.reagent.ItemCraftCostBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 08/08/2019
 */
public class BestCraftGridBean implements Serializable {

    public static final String PROFIT = "profit";
    public static final String SELL_PRICE = "sellPrice";
    public static final String REAGENT_PRICE = "reagentPrice";
    private SpellBean receiptBean;
    private ItemBean itemBean;
    private double salesPerDay;
    private int auctionsCount;
    @FieldName(PROFIT)
    private Long profit;
    @FieldName(SELL_PRICE)
    private long sellPrice;
    private ItemCraftCostBean craftCostBean;

    private ItemRegionSaleBean itemRegionSaleBean;
    private List<ItemStatsBean> itemStatsBeanList;
    private boolean allReagentsInAuction = true;

    public ItemBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(ItemBean itemBean) {
        this.itemBean = itemBean;
    }

    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getWowHeadUrl() {
        return "https://ru.wowhead.com/item=" + itemBean.getItemId();
    }

    public SpellBean getReceiptBean() {
        return receiptBean;
    }

    public void setReceiptBean(SpellBean receiptBean) {
        this.receiptBean = receiptBean;
    }

    public String name() {
        return receiptBean.name();
    }

    public int getAuctionsCount() {
        return auctionsCount;
    }

    public void setAuctionsCount(int auctionsCount) {
        this.auctionsCount = auctionsCount;
    }

    public ItemRegionSaleBean getItemRegionSaleBean() {
        return itemRegionSaleBean;
    }

    public void setItemRegionSaleBean(ItemRegionSaleBean itemRegionSaleBean) {
        this.itemRegionSaleBean = itemRegionSaleBean;
    }

    public boolean isAllReagentsInAuction() {
        return allReagentsInAuction;
    }

    public void setAllReagentsInAuction(boolean allReagentsInAuction) {
        this.allReagentsInAuction = allReagentsInAuction;
    }

    public List<ItemStatsBean> getItemStatsBeanList() {
        return itemStatsBeanList;
    }

    public void setItemStatsBeanList(List<ItemStatsBean> itemStatsBeanList) {
        this.itemStatsBeanList = itemStatsBeanList;
    }

    public double getSalesPerDay() {
        return salesPerDay;
    }

    public void setSalesPerDay(double salesPerDay) {
        this.salesPerDay = salesPerDay;
    }

    public ItemCraftCostBean getCraftCostBean() {
        return craftCostBean;
    }

    public void setCraftCostBean(ItemCraftCostBean craftCostBean) {
        this.craftCostBean = craftCostBean;
    }
}
