/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.html;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;

/**
 * @author Usov Andrey
 * @since 2020-10-05
 */
@Tag("form")
public class Form extends HtmlContainer {

    public Form(Component... components) {
        super(components);
    }

    public static Form form(String className, Component... components) {
        return new Form(components).className(className);
    }

    public Form className(String className) {
        setClassName(className);
        return this;
    }

}
