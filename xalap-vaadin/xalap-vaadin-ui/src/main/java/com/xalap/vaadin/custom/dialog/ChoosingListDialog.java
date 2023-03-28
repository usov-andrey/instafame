/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.dialog;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.function.SerializableConsumer;
import com.xalap.vaadin.custom.grid.GridPanel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
public abstract class ChoosingListDialog<GridBean extends Serializable, ChoosingBean> extends Dialog {

    protected GridPanel<GridBean> gridPanel;
    private SerializableConsumer<ChoosingBean> choosingListener;

    protected ChoosingListDialog(Function<GridBean, ChoosingBean> choosingBeanFunction,
                                 Function<Class<?>, GridPanel<GridBean>> gridPanelSupplier) {
        gridPanel = gridPanelSupplier.apply(getClass());
        gridPanel.columns().addComponent("Выбор",
                gridBean -> new NativeButton("Выбрать", event -> {
                    ChoosingBean choosingBean = choosingBeanFunction.apply(gridBean);
                    choosingListener.accept(choosingBean);
                    close();
                }));
        setWidth("900px");
        setHeight("600px");
        add(gridPanel);
    }

    public void open(SerializableConsumer<ChoosingBean> choosingListener) {
        this.choosingListener = choosingListener;
        open();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        gridPanel.reDraw();
    }
}
