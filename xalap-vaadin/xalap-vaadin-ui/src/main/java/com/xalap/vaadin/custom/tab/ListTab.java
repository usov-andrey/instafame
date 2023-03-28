/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.tab;

import com.vaadin.flow.component.AttachEvent;
import com.xalap.vaadin.custom.grid.GridPanel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Вкладка с гридом
 * @author Усов Андрей
 * @since 12/06/2019
 */
public abstract class ListTab<B extends Serializable, PARENT_BEAN extends Serializable> extends Tab<PARENT_BEAN> {

    protected GridPanel<B> gridPanel;

    protected ListTab() {
        this(GridPanel.createDefault(ListTab.class));
        add(gridPanel);
    }

    protected ListTab(Function<Class<?>, GridPanel<B>> beanGridPanelFunction) {
        gridPanel = beanGridPanelFunction.apply(getClass());
        add(gridPanel);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        gridPanel.reDraw();
    }

}
