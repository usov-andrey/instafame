/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.html;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;

/**
 * @author Usov Andrey
 * @since 2020-10-05
 */
@Tag("option")
public class Option extends HtmlContainer {

    public Option(String value, String caption) {
        getElement().setProperty("value", value);
        setText(caption);
    }
}
