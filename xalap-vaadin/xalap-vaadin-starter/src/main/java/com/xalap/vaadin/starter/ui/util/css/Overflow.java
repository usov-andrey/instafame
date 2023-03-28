/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css;

public enum Overflow {

    AUTO("auto"), HIDDEN("hidden"), SCROLL("scroll"), VISIBLE("visible");

    private final String value;

    Overflow(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
