/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

/**
 * @author Usov Andrey
 * @since 2020-10-06
 */
public class Label extends com.vaadin.flow.component.html.Label {

    public Label(String text) {
        super(text);
    }

    public static Label label(String className, String text) {
        return new Label(text).className(className);
    }

    public Label className(String className) {
        setClassName(className);
        return this;
    }

    public Label forInput(String inputName) {
        getElement().setAttribute("for", inputName);
        return this;
    }

    public Label bold() {
        getElement().getStyle().set("font-weight", "bold");
        return this;
    }
}
