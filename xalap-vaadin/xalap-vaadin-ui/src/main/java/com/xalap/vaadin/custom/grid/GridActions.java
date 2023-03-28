/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.vaadin.custom.component.ButtonWithLog;

/**
 * Кнопки в строчке грида
 *
 * @author Усов Андрей
 * @since 25/07/2019
 */
public class GridActions<B> {

    private VerticalLayout layout;
    private B bean;
    private Grid<B> grid;

    void draw(B bean, Grid<B> grid, VerticalLayout layout) {
        this.bean = bean;
        this.grid = grid;
        this.layout = layout;
    }

    public GridActions<B> add(String caption, Runnable runnable) {
        layout.add(new ButtonWithLog(caption, runnable));
        return this;
    }

    public GridActions<B> add(Component component) {
        layout.add(component);
        return this;
    }

    public GridActions<B> addWithReload(String caption, Runnable runnable) {
        return add(caption, () -> {
            runnable.run();
            grid.getDataProvider().refreshAll();
        });
    }

    @Override
    public String toString() {
        return "GridActions{" +
                ", bean=" + bean +
                ", grid=" + grid +
                '}';
    }
}
