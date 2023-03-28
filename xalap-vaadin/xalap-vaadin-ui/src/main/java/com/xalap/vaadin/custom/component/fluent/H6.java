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
public class H6 extends com.vaadin.flow.component.html.H6 {

    public H6(Component... components) {
        super(components);
    }

    public H6(String text) {
        super(text);
    }

    public static H6 h6(String className, String text) {
        return new H6(text).className(className);
    }

    public H6 className(String className) {
        setClassName(className);
        return this;
    }
}
