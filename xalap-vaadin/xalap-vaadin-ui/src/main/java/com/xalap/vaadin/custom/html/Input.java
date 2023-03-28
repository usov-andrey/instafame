/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.html;

import com.vaadin.flow.component.*;

/**
 * @author Usov Andrey
 * @since 2020-10-05
 */
@Tag("input")
public class Input extends HtmlContainer implements Focusable<Input>, InputNotifier {

    public Input(String name) {
        getElement().setAttribute("name", name);
    }

    public static Input input(String className, String name) {
        return new Input(name).className(className);
    }

    public Input className(String className) {
        setClassName(className);
        return this;
    }

    public String getValue() {
        return getElement().getProperty("value");
    }

    public Input setValue(String value) {
        getElement().setProperty("value", value);
        return this;
    }

    public Input autoComplete(String value) {
        getElement().setAttribute("autocomplete", value);
        return this;
    }

    public Input placeHolder(String value) {
        getElement().setAttribute("placeholder", value);
        return this;
    }

    public Input number() {
        return type("number");
    }

    public Input email() {
        autoComplete("email");
        return type("email");
    }

    public Input checkbox(boolean isChecked) {
        checked(isChecked);
        return type("checkbox");
    }

    public Input id(String id) {
        getElement().setAttribute("id", id);
        return this;
    }

    public Input required() {
        getElement().setAttribute("required", "");
        return this;
    }

    public Input password() {
        autoComplete("password");
        return type("password");
    }

    private Input type(String value) {
        getElement().setAttribute("type", value);
        return this;
    }

    public Input text() {
        return type("text");
    }

    public Input radio(boolean isChecked) {
        checked(isChecked);
        return type("radio");
    }

    public Input checked(boolean isChecked) {
        if (isChecked) {
            getElement().setAttribute("checked", "");
        } else {
            getElement().removeAttribute("checked");
        }
        return this;
    }

    public Input onChange(ComponentEventListener<Input.ChangeEvent> listener) {
        addListener(Input.ChangeEvent.class, listener);
        return this;
    }

    public Input onInput(ComponentEventListener<Input.InputEvent> listener) {
        addListener(Input.InputEvent.class, listener);
        return this;
    }

    public Input onFocus(ComponentEventListener<FocusEvent<Input>> listener) {
        addFocusListener(listener);
        return this;
    }

    public Input search() {
        return type("search");
    }

    @DomEvent("change")
    public static class ChangeEvent extends ComponentEvent<Input> {
        public ChangeEvent(Input source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    @DomEvent("input")
    public static class InputEvent extends ComponentEvent<Input> {
        public InputEvent(Input source, boolean fromClient) {
            super(source, fromClient);
        }
    }

}
