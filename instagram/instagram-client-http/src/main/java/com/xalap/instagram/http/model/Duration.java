/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;


import com.xalap.framework.utils.DateHelper;

import java.util.Date;

/**
 * @author Усов Андрей
 * @since 05.05.17
 */
public class Duration {

    private final Long value;

    public Duration(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public String getText() {
        if (value == null) {
            return "null";
        }
        return DateHelper.getDateTime(new Date(System.currentTimeMillis() + value));
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        } else {
            return getText();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Duration duration = (Duration) o;

        return value != null ? value.equals(duration.value) : duration.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
