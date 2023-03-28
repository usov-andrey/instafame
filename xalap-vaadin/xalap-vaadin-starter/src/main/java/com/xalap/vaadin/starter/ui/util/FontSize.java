/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util;

public enum FontSize {

    XXS("var(--lumo-font-size-xxs)"),
    XS("var(--lumo-font-size-xs)"),
    S("var(--lumo-font-size-s)"),
    M("var(--lumo-font-size-m)"),
    L("var(--lumo-font-size-l)"),
    XL("var(--lumo-font-size-xl)"),
    XXL("var(--lumo-font-size-xxl)"),
    XXXL("var(--lumo-font-size-xxxl)");

    private final String value;

    FontSize(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
