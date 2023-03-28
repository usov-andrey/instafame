
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.robokassa.receipt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "quantity",
        "sum",
        "payment_method",
        "payment_object",
        "tax"
})
public class RobokassaReceiptItem {

    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("sum")
    private Integer sum;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("payment_object")
    private String paymentObject;
    @JsonProperty("tax")
    private String tax;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("sum")
    public Integer getSum() {
        return sum;
    }

    @JsonProperty("sum")
    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @JsonProperty("payment_method")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("payment_method")
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty("payment_object")
    public String getPaymentObject() {
        return paymentObject;
    }

    @JsonProperty("payment_object")
    public void setPaymentObject(String paymentObject) {
        this.paymentObject = paymentObject;
    }

    @JsonProperty("tax")
    public String getTax() {
        return tax;
    }

    @JsonProperty("tax")
    public void setTax(String tax) {
        this.tax = tax;
    }

}
