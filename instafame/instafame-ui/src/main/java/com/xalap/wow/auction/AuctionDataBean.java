package com.xalap.wow.auction;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * @author Usov Andrey
 * @since 02.06.2022
 */
@Document
public class AuctionDataBean {

    @Id
    private Integer id;
    private Integer auctionHouseId;
    private Integer realmId;
    private AuctionData data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuctionHouseId() {
        return auctionHouseId;
    }

    public void setAuctionHouseId(Integer auctionHouseId) {
        this.auctionHouseId = auctionHouseId;
    }

    public Integer getRealmId() {
        return realmId;
    }

    public void setRealmId(Integer realmId) {
        this.realmId = realmId;
    }

    public AuctionData getData() {
        return data;
    }

    public void setData(AuctionData data) {
        this.data = data;
    }
}
