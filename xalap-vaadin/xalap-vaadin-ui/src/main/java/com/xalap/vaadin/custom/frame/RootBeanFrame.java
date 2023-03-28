/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.frame;

import com.vaadin.flow.component.AttachEvent;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.vaadin.custom.data.DataChangeListener;
import com.xalap.vaadin.custom.data.DataHolder;
import com.xalap.vaadin.custom.tab.EntityTabsComponent;

/**
 * @author Усов Андрей
 * @since 10/07/2019
 */
public abstract class RootBeanFrame<B extends IdHolder<?>> extends NavigationFrame {

    private final DataHolder<B> beanHolder = new DataHolder<>();
    private final EntityTabsComponent<B> tabs = new EntityTabsComponent<>();

    protected B getBean() {
        return beanHolder.getValue();
    }

    public void setBean(B bean) {
        tabs.setParentBean(bean);
        beanHolder.setValue(bean);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        tabs.draw();
    }

    protected void addListener(DataChangeListener<B> listener) {
        beanHolder.addListener(listener);
    }

    public EntityTabsComponent<B> withTabs() {
        return tabs;
    }
}
