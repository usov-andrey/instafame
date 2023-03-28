
/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.orderstatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "order_statuses"
})
@Generated("jsonschema2pojo")
public class YandexOrderStatuses {

    @JsonProperty("order_statuses")
    private List<YandexOrderStatus> orderStatuses = null;

    @JsonProperty("order_statuses")
    public List<YandexOrderStatus> getOrderStatuses() {
        return orderStatuses;
    }

    @JsonProperty("order_statuses")
    public void setOrderStatuses(List<YandexOrderStatus> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }

}
