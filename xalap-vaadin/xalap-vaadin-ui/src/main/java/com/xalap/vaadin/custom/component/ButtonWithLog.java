/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.shared.Registration;
import com.xalap.vaadin.custom.log.LogicCallInfo;

/**
 * Добавлен вывод в лог при нажатии на кнопку
 * @author Усов Андрей
 * @since 17/12/2019
 */
public class ButtonWithLog extends com.vaadin.flow.component.button.Button {

    private final LogicCallInfo callInfo = new LogicCallInfo();

    public ButtonWithLog(String text, Component icon) {
        super(text, icon);
    }

    public ButtonWithLog(String text) {
        super(text);
    }

    public ButtonWithLog(String text, Runnable runnable) {
        super(text, event -> runnable.run());
    }

    public ButtonWithLog(String text, Component icon, Runnable runnable) {
        super(text, icon, event -> runnable.run());
    }

    @Override
    public Registration addClickListener(ComponentEventListener<ClickEvent<com.vaadin.flow.component.button.Button>> listener) {
        return super.addClickListener(event -> {
            callInfo.log("Нажата кнопка " + event.getSource().getText());
            listener.onComponentEvent(event);
        });
    }

    public ButtonWithLog variants(ButtonVariant... variants) {
        addThemeVariants(variants);
        return this;
    }
}
