/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

/**
 * Кнопка, которая после выполнений действия обновляет текущую страницу
 *
 * @author Усов Андрей
 * @since 07/05/2019
 */
public class PageReloadButton extends Button {

    public PageReloadButton(String text, ComponentEventListener<ClickEvent<Button>> clickListener) {
        super(text, event -> {
            clickListener.onComponentEvent(event);
            UI.getCurrent().getPage().reload();
        });
    }
}
