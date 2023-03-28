/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util;

public enum IconSize {

    S("size-s"),
    M("size-m"),
    L("size-l");

    private final String style;

    IconSize(String style) {
        this.style = style;
    }

    public String getClassName() {
        return style;
    }

}
