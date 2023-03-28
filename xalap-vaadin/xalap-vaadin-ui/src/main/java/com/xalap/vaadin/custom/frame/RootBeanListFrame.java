/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.frame;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.function.SerializableFunction;
import com.xalap.framework.domain.entity.EntityWithIdService;
import com.xalap.framework.domain.holder.WithId;
import com.xalap.framework.utils.ReflectHelper;
import com.xalap.vaadin.custom.component.entity.EntityForm;
import com.xalap.vaadin.custom.component.entity.EntityWithUpdateService;
import com.xalap.vaadin.custom.data.DataChangeListener;
import com.xalap.vaadin.custom.grid.BeanWithIdGridPanel;
import com.xalap.vaadin.custom.grid.GridDetailsEditor;
import com.xalap.vaadin.custom.grid.GridPanel;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Отображает один бин в списке, а в редактирование другого бина
 *
 * @author Усов Андрей
 * @since 11/06/2019
 */
public abstract class RootBeanListFrame<B extends WithId, GridBean extends Serializable> extends RootListFrame<GridBean> {

    private final GridDetailsEditor<B, GridBean> editor;
    private final SerializableFunction<GridBean, B> beanFunction;

    protected RootBeanListFrame(Function<Class<?>, GridPanel<GridBean>> gridPanelFunction,
                                SerializableFunction<GridBean, B> beanFunction,
                                ServiceRef<? extends EntityWithIdService<B>> entityService) {
        super(gridPanelFunction);
        this.beanFunction = beanFunction;
        Class<B> beanClass = ReflectHelper.getGenericParameterClass(getClass(), RootBeanListFrame.class, 0);
        editor = new GridDetailsEditor<>(gridPanel, beanClass, beanFunction,
                () -> new EntityWithUpdateService<>(entityService,
                b -> reloadData()));
        setViewDetails(editor.createDetailsDrawer());
    }

    protected RootBeanListFrame(SerializableFunction<GridBean, B> beanFunction,
                                ServiceRef<? extends EntityWithIdService<B>> entityService) {
        this(BeanWithIdGridPanel.createBeanDefault(RootListFrame.class), beanFunction, entityService);
    }

    protected List<B> getBeans(Collection<GridBean> beans) {
        return beans.stream().map(beanFunction).collect(Collectors.toList());
    }

    protected void setForm(EntityForm<B> form) {
        editor.setForm(form);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        editor.drawForm();
    }

    /**
     * Обработчик, который будет вызываться после того как данные в гриде получены из источника
     * и отфильтрованы
     */
    protected void setGridDataListener(DataChangeListener<Collection<GridBean>> listener) {
        gridPanel.dataSource().addListener(listener);
    }

    protected void addCreateButton() {
        gridPanel.buttons().add(editor.createAddButton());
    }
}
