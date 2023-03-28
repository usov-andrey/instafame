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
public class H2 extends com.vaadin.flow.component.html.H2 {

    public H2(Component... components) {
        super(components);
    }

    public H2(String text) {
        super(text);
    }

    public static H2 h2(String className, String text) {
        return new H2(text).className(className);
    }

    public static H2 h2(String className, Component... components) {
        return new H2(components).className(className);
    }

    public H2 className(String className) {
        setClassName(className);
        return this;
    }
}
