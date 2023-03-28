/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.util.css;

public enum AlignSelf {

    BASLINE("baseline"), CENTER("center"), END("end"), START("start"), STRETCH(
            "stretch");

    private final String value;

    AlignSelf(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
