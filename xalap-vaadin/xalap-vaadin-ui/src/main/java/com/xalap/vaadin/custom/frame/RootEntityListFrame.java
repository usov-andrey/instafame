/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.frame;

import com.xalap.framework.domain.entity.EntityWithIdService;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.page.PageableEntityService;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.function.Function;

/**
 * Отображает грид с возможностью быстрого редактирования обьекта
 * В списке отображается тот же бин, что и редактируется
 * @author Усов Андрей
 * @since 07/06/2019
 */
public class RootEntityListFrame<B extends IdHolder<?>> extends RootBeanListFrame<B, B> {

    public RootEntityListFrame(
            ServiceRef<? extends PageableEntityService<B, ?>> crudService, GridDefaultSorting sort) {
        super(b -> b, crudService);
        setDataProvider(crudService, sort);
    }

    public RootEntityListFrame(
            Function<Class<?>, GridPanel<B>> gridPanelFunction,
            ServiceRef<? extends PageableEntityService<B, ?>> crudService,
            GridDefaultSorting sort) {
        super(gridPanelFunction, b -> b, crudService);
        setDataProvider(crudService, sort);
    }


    /**
     * @deprecated user instead другой конструктор
     */
    @Deprecated
    public RootEntityListFrame(ServiceRef<? extends EntityWithIdService<B>> crudService) {
        super(b -> b, crudService);
        setMemoryDataProvider(() -> crudService.get().getAll());
    }

    /**
     * @deprecated user instead другой конструктор
     */
    @Deprecated
    public RootEntityListFrame(Function<Class<?>, GridPanel<B>> gridPanelFunction, ServiceRef<? extends EntityWithIdService<B>> crudService) {
        super(gridPanelFunction, b -> b, crudService);
        setMemoryDataProvider(() -> crudService.get().getAll());
    }

}
