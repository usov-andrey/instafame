
package com.xalap.wow.tsm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Id",
        "Name",
        "Level",
        "Class",
        "SubClass",
        "VendorBuy",
        "VendorSell",
        "MarketValue",
        "MinBuyout",
        "Quantity",
        "NumAuctions",
        "HistoricalPrice",
        "RegionMarketAvg",
        "RegionMinBuyoutAvg",
        "RegionQuantity",
        "RegionHistoricalPrice",
        "RegionSaleAvg",
        "RegionAvgDailySold",
        "RegionSaleRate",
        "URL",
        "LastUpdated"
})
public class TsmItemResponse {

    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Level")
    private Long level;
    @JsonProperty("Class")
    private String _class;
    @JsonProperty("SubClass")
    private String subClass;
    @JsonProperty("VendorBuy")
    private Long vendorBuy;
    @JsonProperty("VendorSell")
    private Long vendorSell;
    @JsonProperty("MarketValue")
    private Long marketValue;
    @JsonProperty("MinBuyout")
    private Long minBuyout;
    @JsonProperty("Quantity")
    private Long quantity;
    @JsonProperty("NumAuctions")
    private Long numAuctions;
    @JsonProperty("HistoricalPrice")
    private Long historicalPrice;
    @JsonProperty("RegionMarketAvg")
    private Long regionMarketAvg;
    @JsonProperty("RegionMinBuyoutAvg")
    private Long regionMinBuyoutAvg;
    @JsonProperty("RegionQuantity")
    private Long regionQuantity;
    @JsonProperty("RegionHistoricalPrice")
    private Long regionHistoricalPrice;
    @JsonProperty("RegionSaleAvg")
    private Long regionSaleAvg;
    @JsonProperty("RegionAvgDailySold")
    private Double regionAvgDailySold;
    @JsonProperty("RegionSaleRate")
    private Double regionSaleRate;
    @JsonProperty("URL")
    private String uRL;
    @JsonProperty("LastUpdated")
    private Long lastUpdated;

    @JsonProperty("Id")
    public Long getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Level")
    public Long getLevel() {
        return level;
    }

    @JsonProperty("Level")
    public void setLevel(Long level) {
        this.level = level;
    }

    @JsonProperty("Class")
    public String getClass_() {
        return _class;
    }

    @JsonProperty("Class")
    public void setClass_(String _class) {
        this._class = _class;
    }

    @JsonProperty("SubClass")
    public String getSubClass() {
        return subClass;
    }

    @JsonProperty("SubClass")
    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    @JsonProperty("VendorBuy")
    public Long getVendorBuy() {
        return vendorBuy;
    }

    @JsonProperty("VendorBuy")
    public void setVendorBuy(Long vendorBuy) {
        this.vendorBuy = vendorBuy;
    }

    @JsonProperty("VendorSell")
    public Long getVendorSell() {
        return vendorSell;
    }

    @JsonProperty("VendorSell")
    public void setVendorSell(Long vendorSell) {
        this.vendorSell = vendorSell;
    }

    @JsonProperty("MarketValue")
    public Long getMarketValue() {
        return marketValue;
    }

    @JsonProperty("MarketValue")
    public void setMarketValue(Long marketValue) {
        this.marketValue = marketValue;
    }

    @JsonProperty("MinBuyout")
    public Long getMinBuyout() {
        return minBuyout;
    }

    @JsonProperty("MinBuyout")
    public void setMinBuyout(Long minBuyout) {
        this.minBuyout = minBuyout;
    }

    @JsonProperty("Quantity")
    public Long getQuantity() {
        return quantity;
    }

    @JsonProperty("Quantity")
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("NumAuctions")
    public Long getNumAuctions() {
        return numAuctions;
    }

    @JsonProperty("NumAuctions")
    public void setNumAuctions(Long numAuctions) {
        this.numAuctions = numAuctions;
    }

    @JsonProperty("HistoricalPrice")
    public Long getHistoricalPrice() {
        return historicalPrice;
    }

    @JsonProperty("HistoricalPrice")
    public void setHistoricalPrice(Long historicalPrice) {
        this.historicalPrice = historicalPrice;
    }

    @JsonProperty("RegionMarketAvg")
    public Long getRegionMarketAvg() {
        return regionMarketAvg;
    }

    @JsonProperty("RegionMarketAvg")
    public void setRegionMarketAvg(Long regionMarketAvg) {
        this.regionMarketAvg = regionMarketAvg;
    }

    @JsonProperty("RegionMinBuyoutAvg")
    public Long getRegionMinBuyoutAvg() {
        return regionMinBuyoutAvg;
    }

    @JsonProperty("RegionMinBuyoutAvg")
    public void setRegionMinBuyoutAvg(Long regionMinBuyoutAvg) {
        this.regionMinBuyoutAvg = regionMinBuyoutAvg;
    }

    @JsonProperty("RegionQuantity")
    public Long getRegionQuantity() {
        return regionQuantity;
    }

    @JsonProperty("RegionQuantity")
    public void setRegionQuantity(Long regionQuantity) {
        this.regionQuantity = regionQuantity;
    }

    @JsonProperty("RegionHistoricalPrice")
    public Long getRegionHistoricalPrice() {
        return regionHistoricalPrice;
    }

    @JsonProperty("RegionHistoricalPrice")
    public void setRegionHistoricalPrice(Long regionHistoricalPrice) {
        this.regionHistoricalPrice = regionHistoricalPrice;
    }

    @JsonProperty("RegionSaleAvg")
    public Long getRegionSaleAvg() {
        return regionSaleAvg;
    }

    @JsonProperty("RegionSaleAvg")
    public void setRegionSaleAvg(Long regionSaleAvg) {
        this.regionSaleAvg = regionSaleAvg;
    }

    public Double getRegionAvgDailySold() {
        return regionAvgDailySold;
    }

    public void setRegionAvgDailySold(Double regionAvgDailySold) {
        this.regionAvgDailySold = regionAvgDailySold;
    }

    @JsonProperty("RegionSaleRate")
    public Double getRegionSaleRate() {
        return regionSaleRate;
    }

    @JsonProperty("RegionSaleRate")
    public void setRegionSaleRate(Double regionSaleRate) {
        this.regionSaleRate = regionSaleRate;
    }

    @JsonProperty("URL")
    public String getURL() {
        return uRL;
    }

    @JsonProperty("URL")
    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    @JsonProperty("LastUpdated")
    public Long getLastUpdated() {
        return lastUpdated;
    }

    @JsonProperty("LastUpdated")
    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
