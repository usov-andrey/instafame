/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class Div extends com.vaadin.flow.component.html.Div {

    public Div(String text) {
        setText(text);
    }

    public Div(Component... components) {
        super(components);
    }

    public static Div div(String className, String text) {
        return new Div(text).className(className);
    }

    public static Div div(String className, Component... components) {
        return div(components).className(className);
    }

    public static Div div(Component... components) {
        return new Div(components);
    }

    public Div className(String className) {
        setClassName(className);
        return this;
    }

    public Div classNames(String... classNames) {
        addClassNames(classNames);
        return this;
    }

    public Div widthFull() {
        setWidthFull();
        return this;
    }

    public Div style(String name, String value) {
        getStyle().set(name, value);
        return this;
    }

    public Div style(String value) {
        getElement().setAttribute("style", value);
        return this;
    }

    public Div removeStyles() {
        getElement().removeAttribute("style");
        return this;
    }

    public Div onClick(ComponentEventListener<ClickEvent<com.vaadin.flow.component.html.Div>> listener) {
        addClickListener(listener);
        return this;
    }
}
