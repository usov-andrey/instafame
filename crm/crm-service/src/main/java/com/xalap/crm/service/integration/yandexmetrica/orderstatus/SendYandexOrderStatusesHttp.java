/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.orderstatus;

import com.xalap.crm.service.integration.yandexmetrica.SendYandexHttp;

import java.io.IOException;

/**
 * Синхронизация статусов заказов в CRM и Метрике
 *
 * @author Usov Andrey
 * @since 24.02.2022
 */
public class SendYandexOrderStatusesHttp extends SendYandexHttp {
    private static final String URL = "https://api-metrika.yandex.net/cdp/api/v1/counter/%s/schema/order_statuses";

    public String send(YandexOrderStatuses statuses) throws IOException {
        return exec(URL, statuses);
    }
}