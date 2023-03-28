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
public class H1 extends com.vaadin.flow.component.html.H1 {

    public H1(Component... components) {
        super(components);
    }

    public H1(String text) {
        super(text);
    }

    public static H1 h1(String className, String text) {
        return new H1(text).className(className);
    }

    public static H1 h1(String className, Component... components) {
        return new H1(components).className(className);
    }

    public H1 className(String className) {
        setClassName(className);
        return this;
    }
}
