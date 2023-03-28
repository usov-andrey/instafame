/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css;

public enum Display {

    BLOCK("block"), INLINE("inline"), FLEX("flex"), INLINE_FLEX("inline-flex");

    private final String value;

    Display(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
