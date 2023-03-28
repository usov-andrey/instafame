/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.dataprovider;

import com.xalap.framework.domain.page.PageableService;
import com.xalap.framework.domain.page.dataprovider.PageableDataProvider;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Usov Andrey
 * @since 2020-04-10
 */
public class GridSqlDataProvider<T>
        extends AbstractGridSqlDataProvider<T, Void> {

    public GridSqlDataProvider(ServiceRef<? extends PageableService<T>> pageableService, GridDefaultSorting sort) {
        super(() -> PageableDataProvider.all(pageableService.get()), sort);
    }

    public GridSqlDataProvider(PageableDataProviderResolver<T> dataProviderResolver, GridDefaultSorting sort) {
        super(dataProviderResolver, sort);
    }
}
