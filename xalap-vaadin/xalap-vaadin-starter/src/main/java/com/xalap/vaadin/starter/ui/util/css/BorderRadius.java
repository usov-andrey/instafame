/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css;

public enum BorderRadius {

    S("var(--lumo-border-radius-s)"), M("var(--lumo-border-radius-m)"), L(
            "var(--lumo-border-radius-l)"),

    _50("50%");

    private final String value;

    BorderRadius(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
