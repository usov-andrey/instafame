
/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "orders"
})
@Generated("jsonschema2pojo")
public class YandexOrders {

    @JsonProperty("orders")
    private List<YandexOrder> orders = null;

    @JsonProperty("orders")
    public List<YandexOrder> getOrders() {
        return orders;
    }

    @JsonProperty("orders")
    public void setOrders(List<YandexOrder> orders) {
        this.orders = orders;
    }

}
