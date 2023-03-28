/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

/**
 * @author Usov Andrey
 * @since 2020-10-06
 */
public class Hr extends com.vaadin.flow.component.html.Hr {

    public static Hr hr(String className) {
        return new Hr().className(className);
    }

    public Hr className(String className) {
        setClassName(className);
        return this;
    }
}
