/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css.lumo;

public enum BadgeColor {

    NORMAL("badge"), NORMAL_PRIMARY("badge primary"), SUCCESS(
            "badge success"), SUCCESS_PRIMARY("badge success primary"), ERROR(
            "badge error"), ERROR_PRIMARY(
            "badge error primary"), CONTRAST(
            "badge contrast"), CONTRAST_PRIMARY(
            "badge contrast primary");

    private final String style;

    BadgeColor(String style) {
        this.style = style;
    }

    public String getThemeName() {
        return style;
    }

}
