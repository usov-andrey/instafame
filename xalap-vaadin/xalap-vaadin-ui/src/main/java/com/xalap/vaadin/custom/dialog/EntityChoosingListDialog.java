/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.dialog;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.framework.domain.page.PageableEntityService;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.custom.grid.dataprovider.GridSqlDataProvider;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.function.Function;

/**
 * @author Усов Андрей
 * @since 18/06/2019
 */
public class EntityChoosingListDialog<B extends IdHolder<?>> extends ChoosingListDialog<B, B> {

    public EntityChoosingListDialog() {
        super(b -> b, GridPanel.createDefault(EntityChoosingListDialog.class));
    }

    public EntityChoosingListDialog(Function<Class<?>, GridPanel<B>> gridPanelSupplier) {
        super(b -> b, gridPanelSupplier);
    }

    public EntityChoosingListDialog(
            ServiceRef<? extends PageableEntityService<B, ?>> crudService, Class<B> beanClass, GridDefaultSorting sort) {
        super(b -> b, aClass -> new GridPanel<>(beanClass));
        gridPanel.dataSource().setPageableDataProvider(new GridSqlDataProvider<>(crudService, sort));
        if (IdHolderWithName.class.isAssignableFrom(beanClass)) {
            gridPanel.columns().add("name");
        }
    }
}
