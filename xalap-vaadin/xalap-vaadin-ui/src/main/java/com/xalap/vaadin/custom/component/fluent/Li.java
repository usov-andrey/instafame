/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;

/**
 * @author Usov Andrey
 * @since 2020-05-10
 */
public class Li extends com.vaadin.flow.component.html.ListItem {

    public Li(Component... components) {
        super(components);
    }

    public Li(String text) {
        super(text);
    }

    public static Li li(String className, String text) {
        return new Li(text).className(className);
    }

    public static Li li(String className, Component... components) {
        return new Li(components).className(className);
    }

    public Li className(String className) {
        setClassName(className);
        return this;
    }
}
