/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Вывод уведомлений в лог
 * 
 * @author Усов Андрей
 * @since 31/01/2019
 */
public class LogNotificationService implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(LogNotificationService.class);

    @Override
    public void sendMessage(String message) {
        //Ничего не делаем
        log.debug("Message to notificationService:" + message);
    }
}
