/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service;

import com.xalap.crm.service.scheduler.SchedulerTask;
import com.xalap.framework.exception.IORuntimeException;
import com.xalap.framework.utils.HttpClientUtils;

import java.io.IOException;

/**
 * Проверка работоспособности api
 *
 * @author Усов Андрей
 * @since 09/10/2019
 */
public class InstaFameCheckApiTask extends SchedulerTask {

    @Override
    protected void doRun() {
        try {
            String responseBody = HttpClientUtils.get("https://api.instafame.ru/instagram?term=andreyusov.ru");
            if (!responseBody.contains("andreyusov.ru")) {
                throw new IllegalStateException("Error on check: https://api.instafame.ru/instagram?term=andreyusov.ru  Response:" + responseBody);
            }
        } catch (IOException e) {
            throw new IORuntimeException("Error on check api.instafame.ru", e);
        }
    }
}
