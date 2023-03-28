/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.sort;

import java.util.Arrays;
import java.util.List;

/**
 * Сортировка
 *
 * @author Usov Andrey
 * @since 2020-10-15
 */
public class Sort {

    private static final Sort UNSORTED = Sort.by();

    private final List<SortOrder> orders;

    public Sort(List<SortOrder> orders) {
        this.orders = orders;
    }

    public static Sort by(SortOrder... orders) {
        return new Sort(Arrays.asList(orders));
    }

    public static Sort by(List<SortOrder> orders) {
        return orders.isEmpty() ? UNSORTED : new Sort(orders);
    }

    public List<SortOrder> getOrders() {
        return orders;
    }

}
