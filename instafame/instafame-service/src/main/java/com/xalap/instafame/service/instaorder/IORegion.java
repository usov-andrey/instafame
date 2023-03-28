/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

/**
 * Все СНГ
 * Россия
 * Украина
 * Беларусь
 * Казахстан
 *
 * @author Usov Andrey
 * @since 2020-10-07
 */
public enum IORegion {
    /**
     * Все СНГ
     */
    allRussian("Все СНГ"),
    /**
     * Россия
     */
    russia("Россия"),
    /**
     * Украина
     */
    ukraine("Украина"),
    /**
     * Беларусь
     */
    belarus("Беларусь"),
    /**
     * Казахстан
     */
    kazakhstan("Казахстан");

    private final String value;

    IORegion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
