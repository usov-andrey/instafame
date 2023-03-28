/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.sort;

/**
 * Сортировка по столбцу
 *
 * @author Usov Andrey
 * @since 2020-10-15
 */
public class SortOrder {

    private final SortDirection direction;
    private final String property;

    public SortOrder(SortDirection direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
    }
}
