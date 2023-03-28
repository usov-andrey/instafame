/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;

/**
 * @author Usov Andrey
 * @since 2020-10-06
 */
@Tag("b")
public class Bold extends HtmlContainer {

    public Bold(Component... components) {
        super(components);
    }

    public Bold(String text) {
        super();
        setText(text);
    }

    public static Bold bold(String className, String text) {
        return new Bold(text).className(className);
    }

    public Bold className(String className) {
        setClassName(className);
        return this;
    }
}
