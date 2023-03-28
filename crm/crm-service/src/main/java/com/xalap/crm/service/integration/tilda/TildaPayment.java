/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.tilda;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * {"orderid":"1892441700",
 * "products":["Премиум 1000 подписчиков 1000 лайков =1090"],
 * "promocode":"ПОДАРОК",
 * "discountvalue":"15%",
 * "discount":"163.500",
 * "subtotal":"1090",
 * "amount":"926.5"
 * }
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "orderid",
        "products",
        "promocode",
        "discountvalue",
        "discount",
        "subtotal",
        "amount"
})
public class TildaPayment {

    @JsonProperty("orderid")
    private String orderid;
    @JsonProperty("products")
    private List<String> products = null;
    @JsonProperty("promocode")
    private String promocode;//"ПОДАРОК"
    @JsonProperty("discountvalue")
    private String discountvalue;//"15%"
    @JsonProperty("discount")
    private String discount;//"163.500"
    @JsonProperty("subtotal")
    private String subtotal;//"1090"
    @JsonProperty("amount")
    private String amount;//"926.5"

    @JsonProperty("orderid")
    public String getOrderid() {
        return orderid;
    }

    @JsonProperty("orderid")
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @JsonProperty("products")
    public List<String> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<String> products) {
        this.products = products;
    }

    @JsonProperty("promocode")
    public String getPromocode() {
        return promocode;
    }

    @JsonProperty("promocode")
    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    @JsonProperty("discountvalue")
    public String getDiscountvalue() {
        return discountvalue;
    }

    @JsonProperty("discountvalue")
    public void setDiscountvalue(String discountvalue) {
        this.discountvalue = discountvalue;
    }

    @JsonProperty("discount")
    public String getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @JsonProperty("subtotal")
    public String getSubtotal() {
        return subtotal;
    }

    @JsonProperty("subtotal")
    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TildaPayment{" +
                "orderid='" + orderid + '\'' +
                ", products=" + products +
                ", promocode='" + promocode + '\'' +
                ", discountvalue='" + discountvalue + '\'' +
                ", discount='" + discount + '\'' +
                ", subtotal='" + subtotal + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
