/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;

/**
 * @author Usov Andrey
 * @since 2020-10-06
 */
public class Header extends com.vaadin.flow.component.html.Header {

    public Header(Component... components) {
        super(components);
    }

    public Header(String text) {
        this(new Text(text));
    }

    public static Header header(String className, String text) {
        return new Header(text).className(className);
    }

    public static Header header(String className, Component... components) {
        return new Header(components).className(className);
    }

    public Header className(String className) {
        setClassName(className);
        return this;
    }
}
