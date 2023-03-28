/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css;

public enum BoxSizing {

    BORDER_BOX("border-box"), CONTENT_BOX("content-box");

    private final String value;

    BoxSizing(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
