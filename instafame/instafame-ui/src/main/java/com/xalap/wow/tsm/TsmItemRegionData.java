package com.xalap.wow.tsm;

/**
 * @author Usov Andrey
 * @since 31.05.2022
 */
public class TsmItemRegionData {

    private long itemString;
    private long regionMarketValue;
    private long regionHistorical;
    private long regionSale;
    private long regionSoldPerDay;
    private long regionSalePercent;

    public long getItemString() {
        return itemString;
    }

    public void setItemString(long itemString) {
        this.itemString = itemString;
    }

    public long getRegionMarketValue() {
        return regionMarketValue;
    }

    public void setRegionMarketValue(long regionMarketValue) {
        this.regionMarketValue = regionMarketValue;
    }

    public long getRegionHistorical() {
        return regionHistorical;
    }

    public void setRegionHistorical(long regionHistorical) {
        this.regionHistorical = regionHistorical;
    }

    public long getRegionSale() {
        return regionSale;
    }

    public void setRegionSale(long regionSale) {
        this.regionSale = regionSale;
    }


    public void setRegionSoldPerDay(long regionSoldPerDay) {
        this.regionSoldPerDay = regionSoldPerDay;
    }

    public void setRegionSalePercent(long regionSalePercent) {
        this.regionSalePercent = regionSalePercent;
    }

    public double soldPerDay() {
        double result = regionSoldPerDay;
        return result / 100;
    }

    public double salePercent() {
        double result = regionSalePercent;
        return result / 100;
    }

    @Override
    public String toString() {
        return "TsmItemRegionData{" +
                "itemString=" + itemString +
                ", regionMarketValue=" + regionMarketValue +
                ", regionHistorical=" + regionHistorical +
                ", regionSale=" + regionSale +
                ", regionSoldPerDay=" + regionSoldPerDay +
                ", regionSalePercent=" + regionSalePercent +
                '}';
    }
}
