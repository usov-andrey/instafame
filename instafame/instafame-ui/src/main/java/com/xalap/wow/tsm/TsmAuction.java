package com.xalap.wow.tsm;

/**
 * Информация из файла TradeSkillMaster
 *
 * @author Usov Andrey
 * @since 31.05.2022
 */
public class TsmAuction {

    private long itemString;
    private long minBuyout;
    private long marketValue;
    private long numAuctions;
    private long quantity;
    private long lastScan;

    public long getItemString() {
        return itemString;
    }

    public void setItemString(long itemString) {
        this.itemString = itemString;
    }

    public long getMinBuyout() {
        return minBuyout;
    }

    public void setMinBuyout(long minBuyout) {
        this.minBuyout = minBuyout;
    }

    public long getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(long marketValue) {
        this.marketValue = marketValue;
    }

    public long getNumAuctions() {
        return numAuctions;
    }

    public void setNumAuctions(long numAuctions) {
        this.numAuctions = numAuctions;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getLastScan() {
        return lastScan;
    }

    public void setLastScan(long lastScan) {
        this.lastScan = lastScan;
    }
}
