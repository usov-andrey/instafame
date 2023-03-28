/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Подставляет в текст компоненты
 *
 * @author Usov Andrey
 * @since 2020-11-04
 */
public class TextToComponentsFormatter {

    private final String text;
    private final Component[] components;

    public TextToComponentsFormatter(String text, Component... components) {
        this.text = text;
        this.components = components;
    }

    /**
     * Из строки вида "You are {0} away from FREE Shipping"
     * мы получаем new Text("You are "), component[0], new Text(" away from FREE Shipping")
     */
    public List<Component> format() {
        List<Component> result = new ArrayList<>();
        int textStart = 0;
        int start = text.indexOf("{");
        while (start > -1) {
            int end = text.indexOf("}", start);
            if (end == -1) {
                throw new IllegalStateException("Not found } after {");
            }
            String number = text.substring(start + 1, end);
            int i = Integer.parseInt(number);
            if (i > components.length - 1) {
                throw new IllegalStateException("Size of components(" + components.length + ") less than index:" + i);
            }
            result.add(new Text(text.substring(textStart, start)));
            result.add(components[i]);
            textStart = end + 1;

            start = text.indexOf("{", textStart);
        }
        result.add(new Text(text.substring(textStart)));
        return result;
    }
}
