/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.sort;

/**
 * Направление сортировки
 *
 * @author Usov Andrey
 * @since 2020-10-15
 */
public enum SortDirection {

    ASC, DESC;

    public boolean isAscending() {
        return this.equals(ASC);
    }
}
