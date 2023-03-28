/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.frame;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.xalap.framework.domain.entity.GetEntityService;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.vaadin.starter.ui.components.navigation.bar.AppBar;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Экран отображения и редактирования сущности
 *
 * @author Усов Андрей
 * @since 06/06/2019
 */
public abstract class RootEntityFrame<B extends IdHolder<ID>, ID> extends RootBeanFrame<B> implements HasUrlParameter<ID> {

    private final SerializableFunction<ID, B> beanProvider;
    private Class<? extends Component> backNavigationTarget;

    protected RootEntityFrame(ServiceRef<? extends GetEntityService<B, ID>> crudService,
                              Class<? extends Component> backNavigationTarget) {
        this(crudService.get()::get, backNavigationTarget);
    }

    protected RootEntityFrame(SerializableFunction<ID, B> beanProvider, Class<? extends Component> backNavigationTarget) {
        this(beanProvider);
        this.backNavigationTarget = backNavigationTarget;
    }

    protected RootEntityFrame(ServiceRef<? extends GetEntityService<B, ID>> entityService) {
        this(entityService.get()::get);
    }

    protected RootEntityFrame(SerializableFunction<ID, B> beanProvider) {
        this.beanProvider = beanProvider;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, ID id) {
        B bean = beanProvider.apply(id);
        setBean(bean);
    }

    @Override
    protected void initAppBar(AppBar appBar) {
        super.initAppBar(appBar);
        if (backNavigationTarget != null) {
            appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);//Отменяем выделение пункта меню

            appBar.getContextIcon().addClickListener(
                    e -> UI.getCurrent().navigate(backNavigationTarget));
        }
    }
}
