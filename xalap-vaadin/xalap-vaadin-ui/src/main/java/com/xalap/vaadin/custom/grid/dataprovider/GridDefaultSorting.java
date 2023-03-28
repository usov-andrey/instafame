/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.dataprovider;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.xalap.framework.domain.sort.Sort;
import com.xalap.framework.domain.sort.SortOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сортировка в гриде
 *
 * @author Usov Andrey
 * @since 2020-04-10
 */
public class GridDefaultSorting implements Serializable {

    private final List<QuerySortOrder> sortList = new ArrayList<>();

    private GridDefaultSorting(SortDirection sortDirection, String... columnNames) {
        for (String columnName : columnNames) {
            sortList.add(new QuerySortOrder(columnName, sortDirection));
        }
    }

    public static GridDefaultSorting asc(String... columnNames) {
        return new GridDefaultSorting(SortDirection.ASCENDING, columnNames);
    }

    public static GridDefaultSorting desc(String... columnNames) {
        return new GridDefaultSorting(SortDirection.DESCENDING, columnNames);
    }

    public void add(QuerySortOrder sort) {
        sortList.add(sort);
    }

    public <T, F> Sort createSort(Query<T, F> q) {
        List<QuerySortOrder> sortOrders;
        if (q.getSortOrders().isEmpty()) {
            sortOrders = sortList;
        } else {
            sortOrders = q.getSortOrders();
        }

        List<SortOrder> orders = sortOrders.stream().map(this::queryOrderToSpringOrder).collect(Collectors.toList());
        return Sort.by(orders);
    }

    private SortOrder queryOrderToSpringOrder(QuerySortOrder queryOrder) {
        return new SortOrder(queryOrder.getDirection() == SortDirection.ASCENDING ?
                com.xalap.framework.domain.sort.SortDirection.ASC : com.xalap.framework.domain.sort.SortDirection.DESC,
                queryOrder.getSorted());
    }
}
