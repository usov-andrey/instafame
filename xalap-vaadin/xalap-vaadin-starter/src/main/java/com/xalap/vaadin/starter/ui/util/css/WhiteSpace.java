/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css;

public enum WhiteSpace {

    NORMAL("normal"), NOWRAP("nowrap"), PRE("pre"), PRE_WRAP(
            "pre-wrap"), PRE_LINE("pre-line");

    private final String value;

    WhiteSpace(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
