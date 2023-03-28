/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.tab;

import com.vaadin.flow.component.AttachEvent;
import com.xalap.framework.domain.entity.EntityWithIdService;
import com.xalap.framework.domain.holder.WithId;
import com.xalap.vaadin.custom.component.entity.EntityWithUpdateService;
import com.xalap.vaadin.custom.grid.GridDetailsEditor;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.starter.ui.views.SplitViewFrame;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Вкладка с гридом с возможностью редактирования строчки в гриде
 * @author Усов Андрей
 * @since 12/06/2019
 */
public class ListDetailsTab<B extends WithId, PARENT extends Serializable> extends ListTab<B, PARENT> {

    private final SplitViewFrame parentFrame;
    protected GridDetailsEditor<B, B> editor;

    public <ID> ListDetailsTab(SplitViewFrame parentFrame, ServiceRef<? extends EntityWithIdService<B>> entityService) {
        this(GridPanel.createDefault(ListDetailsTab.class), parentFrame, entityService);
    }

    protected <ID> ListDetailsTab(Function<Class<?>, GridPanel<B>> beanGridPanelFunction,
                             SplitViewFrame splitParentFrame, ServiceRef<? extends EntityWithIdService<B>> entityService) {
        super(beanGridPanelFunction);
        this.parentFrame = splitParentFrame;
        ServiceRef<EntityWithIdService<B>> entityWithIdServiceServiceRef = () -> new EntityWithUpdateService<>(
                entityService, b -> gridPanel.refreshAll());
        editor = new GridDetailsEditor<>(gridPanel, gridPanel.getBeanClass(), b -> b,
                entityWithIdServiceServiceRef
        ) {
            @Override
            protected void showContent(B bean) {
                //Так как область ViewDetails общая для всех вкладок, нужно ее заполнять только в момент отображения
                parentFrame.setViewDetails(editor.createDetailsDrawer());
                super.showContent(bean);
            }
        };
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        editor.drawForm();
    }

    protected void addCreateButton(Supplier<B> supplier) {
        gridPanel.buttons().add(editor.createAddButton(supplier));
    }
}
