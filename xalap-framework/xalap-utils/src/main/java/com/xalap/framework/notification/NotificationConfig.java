/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.notification;

import com.xalap.framework.utils.StringHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Задаем конфигурацию: если задано в конфигурации значение slack.webHookUrl,
 * то отправляем сообщение в слак иначе в лог.
 *
 * @author Usov Andrey
 * @since 2020-04-02
 */
@Configuration
public class NotificationConfig {

    @Bean
    public NotificationService slackNotificationService(@Value("${slack.webHookUrl:}") String slackUrl) {
        return StringHelper.isNotEmpty(slackUrl) ?
                new SlackNotificationService(slackUrl) :
                new LogNotificationService();
    }

}
