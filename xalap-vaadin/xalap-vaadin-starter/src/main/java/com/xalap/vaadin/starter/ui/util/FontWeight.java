/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util;

public enum FontWeight {

    LIGHTER("lighter"),
    NORMAL("normal"),
    BOLD("bold"),
    BOLDER("bolder"),
    _100("100"),
    _200("200"),
    _300("300"),
    _400("400"),
    _500("500"),
    _600("600"),
    _700("700"),
    _800("800"),
    _900("900");

    private final String value;

    FontWeight(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

