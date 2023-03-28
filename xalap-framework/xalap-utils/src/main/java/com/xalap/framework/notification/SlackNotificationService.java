/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.notification;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

/**
 * Отправка уведомлений в слак
 * 
 * @author Усов Андрей
 * @since 07.09.2018
 */
public class SlackNotificationService implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(SlackNotificationService.class);

    private final SlackApi slackApi;

    public SlackNotificationService(String webHookUrl) {
        this.slackApi = new SlackApi(webHookUrl);
    }

    @Override
    @Async
    public void sendMessage(String message) {
        log.debug("Message to slack:" + message);
        slackApi.call(new SlackMessage(message));
    }

}
