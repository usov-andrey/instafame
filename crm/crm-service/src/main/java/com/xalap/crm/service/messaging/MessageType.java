/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging;

/**
 * @author Усов Андрей
 * @since 28/05/2019
 */
public enum MessageType {

    outbound,//Отправлено
    inbound,//Входящее
    planned,//это значит, что в момент createTime нужно попытаться отправить сообщение
    sending,//Отправляется
    error_on_sending,//Ошибка при отправке
    manual_sending//Требуется ручная отправка
}
