/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.dataprovider;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import com.xalap.framework.domain.page.request.PageRequest;
import com.xalap.framework.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.stream.Stream;

/**
 * @author Usov Andrey
 * @since 2020-04-22
 */
public abstract class AbstractGridSqlDataProvider<T, F>
        extends AbstractBackEndDataProvider<T, F> {

    private final PageableDataProviderResolver<T> dataProviderResolver;
    private final GridDefaultSorting sort;
    private final ArrayList<LongConsumer> sizeResultListeners = new ArrayList<>();
    private final ArrayList<Consumer<List<T>>> pageResultListeners = new ArrayList<>();


    protected AbstractGridSqlDataProvider(PageableDataProviderResolver<T> dataProviderResolver, GridDefaultSorting sort) {
        this.dataProviderResolver = dataProviderResolver;
        this.sort = sort;
    }

    public void addSizeResultListener(LongConsumer sizeCountListener) {
        this.sizeResultListeners.add(sizeCountListener);
    }

    public void addPageResultListener(Consumer<List<T>> pageCountListener) {
        this.pageResultListeners.add(pageCountListener);
    }

    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, F> query) {
        PageRequest pageable = getPageable(query);
        List<T> result = this.fetchFromBackEnd(query, pageable);
        for (Consumer<List<T>> pageResultListener : pageResultListeners) {
            pageResultListener.accept(result);
        }
        return this.fromPageable(result, pageable, query);
    }

    private PageRequest getPageable(Query<T, F> q) {
        Pair<Integer, Integer> pageSizeAndNumber = limitAndOffsetToPageSizeAndNumber(q.getOffset(), q.getLimit());
        return PageRequest.of(pageSizeAndNumber.getSecond(), pageSizeAndNumber.getFirst(), sort.createSort(q));
    }

    private Pair<Integer, Integer> limitAndOffsetToPageSizeAndNumber(int offset, int limit) {
        int lastIndex = offset + limit - 1;
        int maxPageSize = lastIndex + 1;

        for (double pageSize = limit; pageSize <= (double) maxPageSize; ++pageSize) {
            int startPage = (int) ((double) offset / pageSize);
            int endPage = (int) ((double) lastIndex / pageSize);
            if (startPage == endPage) {
                return Pair.of((int) pageSize, startPage);
            }
        }

        return Pair.of(maxPageSize, 0);
    }

    private Stream<T> fromPageable(List<T> items, PageRequest pageable, Query<T, ?> query) {
        int firstRequested = query.getOffset();
        int nrRequested = query.getLimit();
        int firstReturned = (int) pageable.getOffset();
        int firstReal = firstRequested - firstReturned;
        int afterLastReal = firstReal + nrRequested;
        if (afterLastReal > items.size()) {
            afterLastReal = items.size();
        }

        return items.subList(firstReal, afterLastReal).stream();
    }

    /**
     *
     * @param query - запрос, переопределяемый в наследнике
     */
    protected List<T> fetchFromBackEnd(Query<T, F> query, PageRequest pageable) {
        return dataProviderResolver.resolve().getPageFunction().apply(pageable);
    }

    @Override
    protected final int sizeInBackEnd(Query<T, F> query) {
        return sizeWithListener(getSize(query));
    }

    /**
     * @param query - запрос, переопределяемый в наследнике
     */
    protected Long getSize(Query<T, F> query) {
        return dataProviderResolver.resolve().getSizeSupplier().get();
    }

    private int sizeWithListener(Long size) {
        for (LongConsumer sizeResultListener : sizeResultListeners) {
            sizeResultListener.accept(size);
        }
        return size.intValue();
    }
}
