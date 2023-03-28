/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;

/**
 * @author Usov Andrey
 * @since 2020-10-06
 */
public class H3 extends com.vaadin.flow.component.html.H3 {

    public H3(Component... components) {
        super(components);
    }

    public H3(String text) {
        super(text);
    }

    public static H3 h3(String className, String text) {
        return new H3(text).className(className);
    }

    public static H3 h3(String className, Component... components) {
        return new H3(components).className(className);
    }

    public H3 className(String className) {
        setClassName(className);
        return this;
    }
}
