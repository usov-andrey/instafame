/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.page.request;

import com.xalap.framework.domain.sort.Sort;

/**
 * @author Usov Andrey
 * @since 2020-10-15
 */
public class PageRequest {

    private final int page;
    private final int size;
    private final Sort sort;

    public PageRequest(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public static PageRequest of(int page, int size, Sort sort) {
        return new PageRequest(page, size, sort);
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public Sort getSort() {
        return sort;
    }

    public long getOffset() {
        return (long) page * (long) size;
    }
}
