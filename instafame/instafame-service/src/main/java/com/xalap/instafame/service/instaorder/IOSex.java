/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

/**
 * Пол
 *
 * @author Usov Andrey
 * @since 24.04.2021
 */
public enum IOSex {

    MALE("Мужчины"),
    FEMALE("Женщины");

    private final String value;

    IOSex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
