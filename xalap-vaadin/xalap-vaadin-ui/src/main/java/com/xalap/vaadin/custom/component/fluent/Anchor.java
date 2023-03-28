/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;

/**
 * @author Usov Andrey
 * @since 2020-10-06
 */
public class Anchor extends com.vaadin.flow.component.html.Anchor implements ClickNotifier<Anchor> {

    public Anchor(String href, String text) {
        super(href, text);
    }

    public Anchor(String href, Component... components) {
        super(href, components);
    }

    public static Anchor anchor(String className, String text) {
        return new Anchor(text).className(className);
    }

    public static Anchor anchor(String className, String href, Component... components) {
        return new Anchor(href, components).className(className);
    }

    public static Anchor anchor(String className, String href, String text) {
        return new Anchor(href, text).className(className);
    }

    public Anchor className(String className) {
        setClassName(className);
        return this;
    }

    public Anchor onClick(ComponentEventListener<ClickEvent<Anchor>> listener) {
        addClickListener(listener);
        return this;
    }

    public Anchor blank() {
        getElement().setAttribute("target", "_blank");
        return this;
    }
}
