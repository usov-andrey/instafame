/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica;

import com.xalap.framework.json.JsonService;
import com.xalap.framework.logging.HasLog;
import com.xalap.framework.utils.HttpClientUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;

/**
 * Отправка http запросов в Yandex Metrika
 *
 * @author Usov Andrey
 * @since 24.02.2022
 */
public class SendYandexHttp implements HasLog {

    private JsonService jsonService;
    private String token;
    private String counter;

    public void init(JsonService jsonService, String token, String counter) {
        this.jsonService = jsonService;
        this.token = token;
        this.counter = counter;
    }

    protected String exec(String url, Object orders) throws IOException {
        String payload = jsonService.getString(orders);
        String formattedUrl = String.format(url, counter);
        log().debug("Yandex.Metrica request:" + formattedUrl + " body:" + payload);
        String result = HttpClientUtils.execDefaultWithPayload(
                Request.Post(formattedUrl)
                        .addHeader("Authorization", "OAuth " + token),
                ContentType.APPLICATION_JSON, payload);

        log().debug("Yandex.Metrica response:" + result);
        return result;
    }

}
