
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.datalayer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "affiliation",
        "revenue",
        "tax",
        "shipping",
        "coupon"
})
public class ActionField {

    @JsonProperty("id")
    private String id;
    @JsonProperty("affiliation")
    private String affiliation;
    @JsonProperty("revenue")
    private String revenue;
    @JsonProperty("tax")
    private String tax;
    @JsonProperty("shipping")
    private String shipping;
    @JsonProperty("coupon")
    private String coupon;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("affiliation")
    public String getAffiliation() {
        return affiliation;
    }

    @JsonProperty("affiliation")
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @JsonProperty("revenue")
    public String getRevenue() {
        return revenue;
    }

    @JsonProperty("revenue")
    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    @JsonProperty("tax")
    public String getTax() {
        return tax;
    }

    @JsonProperty("tax")
    public void setTax(String tax) {
        this.tax = tax;
    }

    @JsonProperty("shipping")
    public String getShipping() {
        return shipping;
    }

    @JsonProperty("shipping")
    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    @JsonProperty("coupon")
    public String getCoupon() {
        return coupon;
    }

    @JsonProperty("coupon")
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

}
