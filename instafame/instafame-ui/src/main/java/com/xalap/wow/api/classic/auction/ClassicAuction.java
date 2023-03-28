
package com.xalap.wow.api.classic.auction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "item",
        "bid",
        "buyout",
        "quantity",
        "time_left"
})
@Generated("jsonschema2pojo")
public class ClassicAuction {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("item")
    private ClassicItemId item;
    @JsonProperty("bid")
    private Long bid;
    @JsonProperty("buyout")
    private Long buyout;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("time_left")
    private String timeLeft;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("item")
    public ClassicItemId getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(ClassicItemId item) {
        this.item = item;
    }

    @JsonProperty("bid")
    public Long getBid() {
        return bid;
    }

    @JsonProperty("bid")
    public void setBid(Long bid) {
        this.bid = bid;
    }

    @JsonProperty("buyout")
    public Long getBuyout() {
        return buyout;
    }

    @JsonProperty("buyout")
    public void setBuyout(Long buyout) {
        this.buyout = buyout;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("time_left")
    public String getTimeLeft() {
        return timeLeft;
    }

    @JsonProperty("time_left")
    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

}
