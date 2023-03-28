package com.xalap.wow.tsm;

/**
 * @author Usov Andrey
 * @since 31.05.2022
 */
public class TsmItemData {

    private long itemString;
    private long marketValue;
    private long minBuyout;
    private long historical;
    private long numAuctions;

    public long getItemString() {
        return itemString;
    }

    public void setItemString(long itemString) {
        this.itemString = itemString;
    }

    public long getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(long marketValue) {
        this.marketValue = marketValue;
    }

    public long getMinBuyout() {
        return minBuyout;
    }

    public void setMinBuyout(long minBuyout) {
        this.minBuyout = minBuyout;
    }

    public long getHistorical() {
        return historical;
    }

    public void setHistorical(long historical) {
        this.historical = historical;
    }

    public long getNumAuctions() {
        return numAuctions;
    }

    public void setNumAuctions(long numAuctions) {
        this.numAuctions = numAuctions;
    }

    @Override
    public String toString() {
        return "TsmItemData{" +
                "itemString=" + itemString +
                ", marketValue=" + marketValue +
                ", minBuyout=" + minBuyout +
                ", historical=" + historical +
                ", numAuctions=" + numAuctions +
                '}';
    }
}
