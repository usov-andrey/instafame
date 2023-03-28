/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.page.dataprovider;

import com.xalap.framework.domain.page.PageableService;
import com.xalap.framework.domain.page.request.PageRequest;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Поставщик данных постранично
 * @author Usov Andrey
 * @since 2020-04-10
 */
public class PageableDataProvider<T> {

    private final Function<PageRequest, List<T>> pageFunction;
    private final Supplier<Long> sizeSupplier;

    private PageableDataProvider(PageableService<T> pageableService) {
        this(pageableService::findPage, pageableService::count);
    }

    private <V> PageableDataProvider(Function<V, T> creator, PageableDataProvider<V> pageableDataProvider) {
        this(creator, pageableDataProvider.pageFunction, pageableDataProvider.sizeSupplier);
    }

    private <V> PageableDataProvider(Function<V, T> creator, Function<PageRequest, List<V>> pageFunction, Supplier<Long> sizeSupplier) {
        this(pageable -> {
            List<V> vList = pageFunction.apply(pageable);
            return vList.stream().map(creator).collect(Collectors.toList());
        }, sizeSupplier);
    }

    private PageableDataProvider(Function<PageRequest, List<T>> pageFunction, Supplier<Long> sizeSupplier) {
        this.pageFunction = pageFunction;
        this.sizeSupplier = sizeSupplier;
    }

    /**
     * Используется, когда бин сервиса и то, что отображается в гриде не совпадают
     */
    public static <T, V> PageableDataProvider<T> delegate(Function<V, T> creator, PageableDataProvider<V> pageableDataProvider) {
        return new PageableDataProvider<>(creator, pageableDataProvider);
    }

    public static <T, V> PageableDataProvider<T> delegate(Function<V, T> creator, PageableService<V> pageableService) {
        return new PageableDataProvider<>(creator, pageableService::findPage, pageableService::count);
    }

    public static <T> PageableDataProvider<T> page(Function<PageRequest, List<T>> pageFunction,
                                                   Supplier<Long> sizeSupplier) {
        return new PageableDataProvider<>(pageFunction, sizeSupplier);
    }

    /**
     * По-умолчанию обращается к методам findAll и count у {@link PageableService}
     */
    public static <T> PageableDataProvider<T> all(PageableService<T> pageableService) {
        return new PageableDataProvider<>(pageableService);
    }

    public Function<PageRequest, List<T>> getPageFunction() {
        return pageFunction;
    }

    public Supplier<Long> getSizeSupplier() {
        return sizeSupplier;
    }

}
