/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

/**
 * @author Усов Андрей
 * @since 12/01/2019
 */
public enum InstaOrderTaskStatus {

    InProgress("В работе"),//Стоимость считаетс как в charge - remains
    Created("Планируется"),
    Completed("Завершен"),
    /**
     * Заказ был отправлен, но по какой-то причине не был выполнен или
     * был выполнен частично.
     * Количество - remains засчитывается и в текущий результат
     */
    Interrupted("Прерван"),
    /**
     * Проблемы при запуске(например, media была удалена или такой заказ уже был сделан)
     * в счетчиках эта задача просто не считается
     */
    Canceled("Отменен"),
    ManualCheck("Нужна ручная проверка");

    private final String caption;

    InstaOrderTaskStatus(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

}
