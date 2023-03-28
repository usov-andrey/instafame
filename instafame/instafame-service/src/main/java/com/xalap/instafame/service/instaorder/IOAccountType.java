/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

/**
 * Дополнительный фильтр аккаунтов
 *
 * @author Usov Andrey
 * @since 2020-10-07
 */
public enum IOAccountType {
    popular("Популярный"), //Популярный (более 500 подписчиков)
    active("Активный"),//Активный (более 10 публикаций)
    MIN_FOLLOWS("Минимально подписок"),//Минимально подписок (не больше 100 подписок)
    ALL("Все");//10 публ, 10 подписчиков, max 4000 подписок

    private final String value;

    IOAccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
