/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.exception.ServiceWrongResponseException;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.HttpClientUtils;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.task.*;
import org.openqa.selenium.json.JsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 06/07/2019
 */
public abstract class CheatTaskProvider {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JsonService converterService;
    @Autowired
    private CheatTaskProviderService providerService;
    @Autowired
    private CheatTaskService cheatTaskService;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        cheatTaskService.register(this);
    }

    public abstract String getName();

    /**
     * @return Список текущих рабочих задач
     */
    public abstract List<CheatTaskBean> getActualTasks() throws ServiceTemporaryException;

    public abstract void abort(IOTaskBean taskBean) throws ServiceTemporaryException;

    public abstract void update(IOTaskBean bean) throws ServiceTemporaryException;

    /**
     * Восстановить подписчиков
     */
    public abstract void refill(IOTaskBean bean) throws ServiceTemporaryException;

    public abstract void runFollowers(IOFollowersTaskBean taskBean) throws ServiceTemporaryException;

    public abstract void runLikes(IOLikesTaskBean taskBean) throws ServiceTemporaryException;

    public abstract void runComments(IOCommentsTaskBean taskBean) throws ServiceTemporaryException;

    public abstract void runViews(IOViewsTaskBean taskBean) throws ServiceTemporaryException;

    protected String execJson(String url) throws ServiceTemporaryException {
        try {
            String json = HttpClientUtils.get(url);
            log.debug("Get url:{} Json response:{}", url, json);
            if (json.startsWith("An internal server error") || json.startsWith("Connection failed")) {
                throw new ServiceTemporaryException("Error on exec " + url + " response:" + json);
            }
            return json;
        } catch (IOException e) {
            //Иногда сайт может быть недоступен
            throw new ServiceTemporaryException("Error on exec " + url, e);
        }
    }

    protected <T> T exec(String url, Class<T> tClass) throws ServiceTemporaryException {
        String json = execJson(url);
        try {
            return convert(json, tClass);
        } catch (JsonException e) {
            throw new ServiceWrongResponseException("Error on convert json: " + json + "  for request: "
                    + url, e, url, json);
        }
    }

    protected <T> T convert(String json, Class<T> tClass) {
        return converterService.getJson(json, tClass);
    }

    /**
     * @return Доступен сейчас сервис или нет
     */
    public boolean isActive() {
        return providerService.isActive(getName());
    }

    public abstract String getApiKey();

    public abstract String getApiUrl();
}
