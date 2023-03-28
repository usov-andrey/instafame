/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.html;

import com.vaadin.flow.component.*;
import com.xalap.vaadin.custom.log.LogicCallInfo;

/**
 * @author Usov Andrey
 * @since 2020-10-05
 */
@Tag("button")
public class Button extends HtmlContainer implements ClickNotifier<Button>, HasStyle {

    private final LogicCallInfo callInfo = new LogicCallInfo();

    public Button(Component... components) {
        super(components);
        getElement().setAttribute("type", "button");
    }

    public Button(String text) {
        this();
        setText(text);
    }

    public static Button button(String className, String text) {
        return new Button(text).className(className);
    }

    public static Button button(String className, Component... components) {
        return new Button(components).className(className);
    }

    public Button className(String className) {
        setClassName(className);
        return this;
    }

    public Button onClick(ComponentEventListener<ClickEvent<Button>> listener) {
        addClickListener(event -> {
            callInfo.log("Нажата кнопка " + event.getSource().getText());
            listener.onComponentEvent(event);
        });
        return this;
    }

    public Button text(String text) {
        setText(text);
        return this;
    }

    public Button disable() {
        getElement().setAttribute("disabled", "");
        return this;
    }

    public Button enable() {
        getElement().removeAttribute("disabled");
        return this;
    }

    public Button style(String value) {
        getElement().setAttribute("style", value);
        return this;
    }
}
