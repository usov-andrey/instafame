/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.contact;

import com.xalap.crm.service.integration.yandexmetrica.SendYandexHttp;

import java.io.IOException;

/**
 * Отправка контактов в Yandex Metrika
 *
 * @author Usov Andrey
 * @since 24.02.2022
 */
public class SendYandexContactsHttp extends SendYandexHttp {
    private static final String URL = "https://api-metrika.yandex.net/cdp/api/v1/counter/%s/data/contacts?merge_mode=APPEND";

    public String send(YandexContacts contacts) throws IOException {
        return exec(URL, contacts);
    }
}
