package com.xalap.wow.item.auction;

import com.xalap.wow.item.ItemBean;
import com.xalap.wow.professionspell.reagent.ItemCraftCostBean;

import java.io.Serializable;

/**
 * @author Усов Андрей
 * @since 20/12/2019
 */
public class AuctionItemGridBean implements Serializable {

    private ItemBean item;
    private Long minBuyout;
    private int quantity;
    private int aucCount;
    private double salesPerDay;
    private double sellPrice;
    private ItemCraftCostBean craftCostBean;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public Long getMinBuyout() {
        return minBuyout;
    }

    public void setMinBuyout(Long minBuyout) {
        this.minBuyout = minBuyout;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAucCount() {
        return aucCount;
    }

    public void setAucCount(int aucCount) {
        this.aucCount = aucCount;
    }

    public double getSalesPerDay() {
        return salesPerDay;
    }

    public void setSalesPerDay(double salesPerDay) {
        this.salesPerDay = salesPerDay;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public ItemCraftCostBean getCraftCostBean() {
        return craftCostBean;
    }

    public void setCraftCostBean(ItemCraftCostBean craftCostBean) {
        this.craftCostBean = craftCostBean;
    }
}
