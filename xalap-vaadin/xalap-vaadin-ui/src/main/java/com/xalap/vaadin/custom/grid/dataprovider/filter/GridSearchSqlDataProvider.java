/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.dataprovider.filter;

import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.framework.domain.page.FilterPageableService;
import com.xalap.framework.domain.page.dataprovider.PageableDataProvider;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import com.xalap.framework.domain.page.dataprovider.filter.FilterDataProviderResolver;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Фильтр со строкой поиска
 *
 * @author Usov Andrey
 * @since 2020-04-23
 */
public class GridSearchSqlDataProvider<T>
        extends GridFilterSqlDataProvider<T, SearchFilter> {

    public GridSearchSqlDataProvider(ServiceRef<? extends FilterPageableService<T, SearchFilter>> pageableService, GridDefaultSorting sort) {
        super(pageableService, filter -> PageableDataProvider.page(
                pageable -> pageableService.get().findPage(pageable, filter),
                () -> pageableService.get().count(filter)), sort);
    }

    public GridSearchSqlDataProvider(PageableDataProviderResolver<T> noFilterDataProviderResolver,
                                     FilterDataProviderResolver<T, SearchFilter> filterDataProviderResolver,
                                     GridDefaultSorting sort) {
        super(noFilterDataProviderResolver, filterDataProviderResolver, sort);
    }

}
