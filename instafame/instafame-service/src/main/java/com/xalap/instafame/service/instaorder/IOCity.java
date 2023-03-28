/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

/**
 * Москва
 * Санкт-Петербург
 *
 * @author Usov Andrey
 * @since 2020-10-07
 */
public enum IOCity {

    MOSCOW("Москва"),
    ST_PETERSBURG("Санкт-Петербург");

    private final String value;

    IOCity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
