/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;

import java.util.List;

/**
 * @author Usov Andrey
 * @since 2020-05-10
 */
public class Paragraph extends com.vaadin.flow.component.html.Paragraph {

    public Paragraph(Component... components) {
        super(components);
    }

    public Paragraph(List<Component> components) {
        components.forEach(this::add);
    }

    public Paragraph(String text) {
        super(text);
    }

    public static Paragraph paragraph(String className, String text) {
        return new Paragraph(text).className(className);
    }

    public static Paragraph paragraph(String className, Component... components) {
        return new Paragraph(components).className(className);
    }

    public Paragraph className(String className) {
        setClassName(className);
        return this;
    }
}
