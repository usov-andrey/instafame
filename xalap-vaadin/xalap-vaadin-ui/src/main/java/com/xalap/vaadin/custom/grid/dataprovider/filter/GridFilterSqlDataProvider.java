/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.dataprovider.filter;

import com.vaadin.flow.data.provider.Query;
import com.xalap.framework.domain.filter.Filter;
import com.xalap.framework.domain.page.PageableService;
import com.xalap.framework.domain.page.dataprovider.PageableDataProvider;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import com.xalap.framework.domain.page.dataprovider.filter.FilterDataProviderResolver;
import com.xalap.framework.domain.page.request.PageRequest;
import com.xalap.vaadin.custom.grid.dataprovider.AbstractGridSqlDataProvider;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Usov Andrey
 * @since 2020-04-06
 */
public class GridFilterSqlDataProvider<T, F extends Filter>
        extends AbstractGridSqlDataProvider<T, F> {

    private final FilterDataProviderResolver<T, F> filterDataProviderResolver;
    private F filter;

    public GridFilterSqlDataProvider(ServiceRef<? extends PageableService<T>> service,
                                     FilterDataProviderResolver<T, F> withFilterResolver, GridDefaultSorting sort) {
        this(() -> PageableDataProvider.all(service.get()), withFilterResolver, sort);
    }

    public GridFilterSqlDataProvider(PageableDataProviderResolver<T> noFilterResolver,
                                     FilterDataProviderResolver<T, F> withFilterResolver, GridDefaultSorting sort) {
        super(noFilterResolver, sort);
        this.filterDataProviderResolver = withFilterResolver;
    }

    public void setFilter(F filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        } else {
            this.filter = filter;
            this.refreshAll();
        }
    }

    @Override
    public int size(Query<T, F> query) {
        return super.size(this.getFilterQuery(query));
    }

    @Override
    public Stream<T> fetch(Query<T, F> query) {
        return super.fetch(this.getFilterQuery(query));
    }

    private Query<T, F> getFilterQuery(Query<T, F> t) {
        return new Query<>(t.getOffset(), t.getLimit(), t.getSortOrders(), t.getInMemorySorting(), this.filter);
    }

    @Override
    protected List<T> fetchFromBackEnd(Query<T, F> query, PageRequest pageable) {
        F f = getFilter(query);
        return f.empty() ?
                super.fetchFromBackEnd(query, pageable) :
                filterDataProviderResolver.resolve(f).getPageFunction().apply(pageable);
    }

    private F getFilter(Query<T, F> query) {
        Optional<F> fOptional = query.getFilter();
        if (fOptional.isEmpty()) {
            throw new IllegalArgumentException("Query.getFilter is empty for query:" + query);
        }
        return fOptional.get();
    }

    @Override
    protected Long getSize(Query<T, F> query) {
        F f = getFilter(query);
        return f.empty() ?
                super.getSize(query) :
                filterDataProviderResolver.resolve(f).getSizeSupplier().get();
    }
}
