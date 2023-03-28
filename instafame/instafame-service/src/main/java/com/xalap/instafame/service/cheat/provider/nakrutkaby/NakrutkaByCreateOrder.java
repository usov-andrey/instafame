/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.nakrutkaby;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Усов Андрей
 * @since 12/01/2019
 */
public class NakrutkaByCreateOrder extends NakrutkaByResponse {

    @JsonProperty("order")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "NakrutkaByCreateOrder{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
