/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.tab;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.framework.domain.entity.EntityWithIdService;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.vaadin.custom.component.entity.EntityForm;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Map;


/**
 * Вкладки + вкладка с полями - Основная информация
 *
 * @author Усов Андрей
 * @since 11/06/2019
 */
public class EntityTabsComponent<B extends IdHolder<?>> extends TabListComponent<Tab<B>> {

    private VerticalLayout header;

    @Override
    protected void addTabs() {
        header = new VerticalLayout();
        add(header);
        header.setVisible(false);
        super.addTabs();
    }

    public EntityTabsComponent<B> beforeTabs(Component... components) {
        header.add(components);
        header.setVisible(true);
        return this;
    }

    public EntityTabsComponent<B> addTab(String caption, Tab<B> body) {
        createTab(caption, body);
        return this;
    }

    public void setParentBean(B parentBean) {
        for (Map.Entry<com.vaadin.flow.component.tabs.Tab, Tab<B>> tabEntry : getTabEntries()) {
            tabEntry.getValue().setParentBean(parentBean);
        }
    }

    public EntityTabsComponent<B> addMainTab(ServiceRef<? extends EntityWithIdService<B>> service) {
        return addTab("Основная информация", new EntityTab<>(service));
    }

    public EntityTabsComponent<B> addMainTab(EntityForm<B> form) {
        return addTab("Основная информация", new EntityTab<>(form));
    }

}
