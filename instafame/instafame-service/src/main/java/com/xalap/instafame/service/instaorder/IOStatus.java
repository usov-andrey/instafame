/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Сначала заказ создается в статусе Created,
 * После оплаты он переводится в Payed
 * После подтверждения инстаграм он переводится в статус Confirmed
 * Далее автоматически система переводит в статус InProgress и автоматически создает задачи
 * Если уже есть заказ от этого клиента с статусе InProgress, то заказ переводится в статус Waiting, из которого после завершения
 * предыдущего заказа текущий переводится в InProgress
 * После InProgress идет перевод в статус Refilling или сразу в статус Completed
 * Из статуса Refilling задачи через месяц переводятся в статус Completed
 *
 * @author Усов Андрей
 * @since 17/04/2019
 */
public enum IOStatus {

    Created {
        @Override
        public boolean payed() {
            return false;
        }
    },
    InProgress,
    Refilling,
    Completed,
    Payed,
    Waiting,
    UserDeleted,
    UserChangedToPrivate,
    Confirmed;

    public static Set<IOStatus> statuses(Predicate<IOStatus> predicate) {
        return Arrays.stream(values()).filter(predicate).collect(Collectors.toSet());
    }

    public boolean payed() {
        return true;
    }

    /*
    all("Все статусы") {
        @Override
        Collection<IOStatus> statuses() {
            return Arrays.asList()
        }
    },
    actived("Активные"),
    stoped("Остановленные"),
    completed("Завершенные"),
    canceled("Отмененные");
     */

}
