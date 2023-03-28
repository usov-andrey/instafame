/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class Span extends com.vaadin.flow.component.html.Span {

    public Span(String text) {
        setText(text);
    }

    public Span(Component... components) {
        super(components);
    }

    public static Span span(String className, String text) {
        return new Span(text).className(className);
    }

    public static Span span(String className, Component... components) {
        return span(components).className(className);
    }

    public static Span span(Component... components) {
        return new Span(components);
    }

    public Span className(String className) {
        setClassName(className);
        return this;
    }
}
