/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import com.vaadin.flow.component.Component;
import com.xalap.vaadin.custom.component.ButtonWithLog;
import com.xalap.vaadin.custom.component.MenuButton;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Кнопки над гридом
 * @author Усов Андрей
 * @since 08/06/2019
 */
public class GridButtons implements Serializable {

    private final GridPanel<?> gridPanel;
    private final Set<Component> buttons = new HashSet<>();
    private final FlexHorizontalLayout actions;
    private MenuButton menuButton;

    public GridButtons(GridPanel<?> gridPanel, FlexHorizontalLayout actions) {
        this.gridPanel = gridPanel;
        this.actions = actions;
    }

    public GridButtons add(String caption, Runnable runnable) {
        add(new ButtonWithLog(caption, runnable));
        return this;
    }

    public GridButtons addWithReload(String caption, Runnable runnable) {
        add(new ButtonWithLog(caption, () -> {
            runnable.run();
            gridPanel.refreshAll();
        }));
        return this;
    }

    public GridButtons add(Component button) {
        actions.add(button);
        buttons.add(button);
        return this;
    }

    public GridButtons clear() {
        for (Component button : buttons) {
            if (button.getParent().isPresent()) {
                actions.remove(button);
            }
        }
        return this;
    }

    public MenuButton menu() {
        if (menuButton == null) {
            menuButton = new MenuButton("Действия");
            add(menuButton);
        }
        return menuButton;
    }

    public FlexHorizontalLayout getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "GridButtons on " + gridPanel;
    }
}
