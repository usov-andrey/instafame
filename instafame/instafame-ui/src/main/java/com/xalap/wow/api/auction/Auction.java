
package com.xalap.wow.api.auction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "auc",
        "item",
        "owner",
        "ownerRealm",
        "bid",
        "buyout",
        "quantity",
        "timeLeft",
        "rand",
        "seed",
        "context"
})
public class Auction implements Serializable {

    @JsonProperty("auc")
    private Long auc;
    @JsonProperty("item")
    private Long item;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("ownerRealm")
    private String ownerRealm;
    @JsonProperty("bid")
    private Long bid;
    @JsonProperty("buyout")
    private Long buyout;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("timeLeft")
    private String timeLeft;
    @JsonProperty("rand")
    private Long rand;
    @JsonProperty("seed")
    private Long seed;
    @JsonProperty("context")
    private Long context;

    public Long getAuc() {
        return auc;
    }

    public void setAuc(Long auc) {
        this.auc = auc;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerRealm() {
        return ownerRealm;
    }

    public void setOwnerRealm(String ownerRealm) {
        this.ownerRealm = ownerRealm;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Long getBuyout() {
        return buyout;
    }

    public void setBuyout(Long buyout) {
        this.buyout = buyout;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Long getRand() {
        return rand;
    }

    public void setRand(Long rand) {
        this.rand = rand;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public Long getContext() {
        return context;
    }

    public void setContext(Long context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auc=" + auc +
                ", item=" + item +
                ", owner='" + owner + '\'' +
                ", ownerRealm='" + ownerRealm + '\'' +
                ", bid=" + bid +
                ", buyout=" + buyout +
                ", quantity=" + quantity +
                ", timeLeft='" + timeLeft + '\'' +
                ", rand=" + rand +
                ", seed=" + seed +
                ", context=" + context +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        return auc.equals(auction.auc);

    }

    @Override
    public int hashCode() {
        return auc.hashCode();
    }

    /**
     * @return До конца аукциона осталось меньше часов, чем возвращаемое значение
     */
    public double hours() {
        return timeLeft.equals("LONG") ? 12 :
                timeLeft.equals("VERY_LONG") ? 48 :
                        timeLeft.equals("MEDIUM") ? 2 :
                                timeLeft.equals("SHORT") ? 0.5 : -1;
    }
}
