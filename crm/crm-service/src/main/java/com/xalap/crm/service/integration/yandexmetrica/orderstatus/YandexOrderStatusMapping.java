/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.orderstatus;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderStatus;

/**
 * Маппинг статусов Метрики на CRM
 *
 * @author Usov Andrey
 * @since 24.02.2022
 */
public enum YandexOrderStatusMapping {

    IN_PROGRESS("IN_PROGRESS", "Created"),
    PAID("PAID", "Paid"),
    CANCELLED("CANCELLED", "Cancelled");

    private final String yandexValue;
    private final String crmValue;

    YandexOrderStatusMapping(String yandexValue, String crmValue) {
        this.yandexValue = yandexValue;
        this.crmValue = crmValue;
    }

    public static YandexOrderStatusMapping getStatus(OrderBean orderBean) {
        if (orderBean.getStatus() == OrderStatus.Canceled) {
            return CANCELLED;
        }
        return orderBean.getStatus().paid() ? PAID : IN_PROGRESS;
    }

    public String getYandexValue() {
        return yandexValue;
    }

    public String getCrmValue() {
        return crmValue;
    }
}
