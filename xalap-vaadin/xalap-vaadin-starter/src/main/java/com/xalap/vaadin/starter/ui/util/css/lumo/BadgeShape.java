/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css.lumo;

public enum BadgeShape {

    NORMAL("normal"), PILL("pill");

    private final String style;

    BadgeShape(String style) {
        this.style = style;
    }

    public String getThemeName() {
        return style;
    }

}
