package com.xalap.wow.auction;

import com.xalap.wow.api.Region;
import com.xalap.wow.item.ItemBean;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
@Entity()
@Table(name = ItemRegionSaleBean.NAME)
public class ItemRegionSaleBean {
    public static final String NAME = "WOW$ItemRegionSale";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemBean item;
    private Region region;
    private Long marketValue;
    private Long minBuyout;
    private Long quantity;
    private Long numAuctions;
    private Long historicalPrice;
    private Long regionMarketAvg;
    private Long regionMinBuyoutAvg;
    private Long regionQuantity;
    private Long regionHistoricalPrice;
    private Long regionSaleAvg;
    private Double regionAvgDailySold;
    private Double regionSaleRate;
    private Date lastModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Long getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Long marketValue) {
        this.marketValue = marketValue;
    }

    public Long getMinBuyout() {
        return minBuyout;
    }

    public void setMinBuyout(Long minBuyout) {
        this.minBuyout = minBuyout;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getNumAuctions() {
        return numAuctions;
    }

    public void setNumAuctions(Long numAuctions) {
        this.numAuctions = numAuctions;
    }

    public Long getHistoricalPrice() {
        return historicalPrice;
    }

    public void setHistoricalPrice(Long historicalPrice) {
        this.historicalPrice = historicalPrice;
    }

    public Long getRegionMarketAvg() {
        return regionMarketAvg;
    }

    public void setRegionMarketAvg(Long regionMarketAvg) {
        this.regionMarketAvg = regionMarketAvg;
    }

    public Long getRegionMinBuyoutAvg() {
        return regionMinBuyoutAvg;
    }

    public void setRegionMinBuyoutAvg(Long regionMinBuyoutAvg) {
        this.regionMinBuyoutAvg = regionMinBuyoutAvg;
    }

    public Long getRegionQuantity() {
        return regionQuantity;
    }

    public void setRegionQuantity(Long regionQuantity) {
        this.regionQuantity = regionQuantity;
    }

    public Long getRegionHistoricalPrice() {
        return regionHistoricalPrice;
    }

    public void setRegionHistoricalPrice(Long regionHistoricalPrice) {
        this.regionHistoricalPrice = regionHistoricalPrice;
    }

    public Long getRegionSaleAvg() {
        return regionSaleAvg;
    }

    public void setRegionSaleAvg(Long regionSaleAvg) {
        this.regionSaleAvg = regionSaleAvg;
    }

    public Double getRegionAvgDailySold() {
        return regionAvgDailySold;
    }

    public void setRegionAvgDailySold(Double regionAvgDailySold) {
        this.regionAvgDailySold = regionAvgDailySold;
    }

    public Double getRegionSaleRate() {
        return regionSaleRate;
    }

    public void setRegionSaleRate(Double regionSaleRate) {
        this.regionSaleRate = regionSaleRate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "ItemRegionSaleBean{" +
                "id=" + id +
                ", item=" + item +
                ", region=" + region +
                ", marketValue=" + marketValue +
                ", minBuyout=" + minBuyout +
                ", quantity=" + quantity +
                ", numAuctions=" + numAuctions +
                ", historicalPrice=" + historicalPrice +
                ", regionMarketAvg=" + regionMarketAvg +
                ", regionMinBuyoutAvg=" + regionMinBuyoutAvg +
                ", regionQuantity=" + regionQuantity +
                ", regionHistoricalPrice=" + regionHistoricalPrice +
                ", regionSaleAvg=" + regionSaleAvg +
                ", regionAvgDailySold=" + regionAvgDailySold +
                ", regionSaleRate=" + regionSaleRate +
                ", lastModified=" + lastModified +
                '}';
    }
}
