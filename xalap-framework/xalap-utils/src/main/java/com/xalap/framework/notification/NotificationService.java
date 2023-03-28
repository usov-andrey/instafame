/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.notification;

/**
 * Отправление уведомлений о работе приложения
 * 
 * @author Усов Андрей
 * @since 07.09.2018
 */
public interface NotificationService {

    /**
     * Отправить сообщение 
     * @param message текст сообщения
     */
    void sendMessage(String message);

}
