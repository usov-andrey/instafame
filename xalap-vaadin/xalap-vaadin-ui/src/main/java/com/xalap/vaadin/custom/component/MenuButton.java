/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.xalap.vaadin.custom.log.LogicCallInfo;

import static com.vaadin.flow.component.icon.VaadinIcon.ANGLE_DOWN;

/**
 * При нажатии на эту кнопку нам не нужно выводить в лог, поэтому наследуемся от обычной vaadin button
 * @author Усов Андрей
 * @since 11/05/2019
 */
public class MenuButton extends com.vaadin.flow.component.button.Button {

    private final ContextMenu contextMenu = new ContextMenu();
    private final LogicCallInfo callInfo = new LogicCallInfo();

    public MenuButton(String caption) {
        super(caption, ANGLE_DOWN.create());
        setIconAfterText(true);

        contextMenu.setTarget(this);
        contextMenu.setOpenOnClick(true);
    }

    /**
     * Добавить пункт меню
     */
    public MenuButton addMenu(String caption, Runnable runnable) {
        contextMenu.addItem(caption, event -> {
            run(runnable, event);
        });
        return this;
    }

    public MenuButton addMenu(Component component, Runnable runnable) {
        contextMenu.addItem(component, event -> {
            run(runnable, event);
        });
        return this;
    }

    private void run(Runnable runnable, ClickEvent<MenuItem> event) {
        callInfo.log("Нажата кнопка " + getText() + " / " + event.getSource().getText());
        runnable.run();
    }
}
