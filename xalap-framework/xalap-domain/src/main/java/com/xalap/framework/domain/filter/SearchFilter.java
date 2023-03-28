/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.filter;

import com.xalap.framework.domain.annotation.FieldImage;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.utils.StringHelper;

/**
 * @author Usov Andrey
 * @since 2020-04-10
 */
public class SearchFilter implements Filter {

    public static final String SEARCH = "search";

    @FieldName(SEARCH)
    @FieldImage("SEARCH")
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public boolean empty() {
        return StringHelper.isEmpty(search);
    }
}
