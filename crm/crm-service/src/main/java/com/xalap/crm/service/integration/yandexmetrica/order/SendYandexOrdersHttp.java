/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.order;

import com.xalap.crm.service.integration.yandexmetrica.SendYandexHttp;

import java.io.IOException;

/**
 * Запрос на отправку данных о заказе
 *
 * @author Usov Andrey
 * @since 23.02.2022
 */
public class SendYandexOrdersHttp extends SendYandexHttp {
    private static final String URL = "https://api-metrika.yandex.net/cdp/api/v1/counter/%s/data/orders?merge_mode=SAVE";

    public String send(YandexOrders orders) throws IOException {
        return exec(URL, orders);
    }

}
