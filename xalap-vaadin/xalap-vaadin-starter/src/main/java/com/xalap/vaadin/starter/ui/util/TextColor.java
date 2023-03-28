/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util;

public enum TextColor {

    HEADER("var(--lumo-header-text-color)"),
    BODY("var(--lumo-body-text-color)"),
    SECONDARY("var(--lumo-secondary-text-color)"),
    TERTIARY("var(--lumo-tertiary-text-color)"),
    DISABLED("var(--lumo-disabled-text-color)"),
    PRIMARY("var(--lumo-primary-text-color)"),
    PRIMARY_CONTRAST("var(--lumo-primary-contrast-color)"),
    ERROR("var(--lumo-error-text-color)"),
    ERROR_CONTRAST("var(--lumo-error-contrast-color)"),
    SUCCESS("var(--lumo-success-text-color)"),
    SUCCESS_CONTRAST("var(--lumo-success-contrast-color)");

    private final String value;

    TextColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
