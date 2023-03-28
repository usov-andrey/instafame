/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;

/**
 * @author Usov Andrey
 * @since 2020-10-06
 */
public class Section extends com.vaadin.flow.component.html.Section {

    public Section(Component... components) {
        super(components);
    }

    public Section(String text) {
        this(new Text(text));
    }

    public static Section section(String className, String text) {
        return new Section(text).className(className);
    }

    public static Section section(String className, Component... components) {
        return new Section(components).className(className);
    }

    public Section className(String className) {
        setClassName(className);
        return this;
    }
}
