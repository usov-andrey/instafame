/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css;

public enum PointerEvents {

    AUTO("auto"), NONE("none");

    private final String value;

    PointerEvents(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
