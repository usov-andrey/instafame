/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;

import java.util.List;

/**
 * @author Usov Andrey
 * @since 2020-11-04
 */
public class Text extends com.vaadin.flow.component.Text {

    public Text(String text) {
        super(text);
    }

    public static Text text(String text) {
        return new Text(text);
    }

    public static List<Component> format(String text, Component... components) {
        return new TextToComponentsFormatter(text, components).format();
    }
}
