/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.filter;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.page.FilterPageableService;

/**
 * Crud + возможность фильтрации
 *
 * @author Usov Andrey
 * @since 14.05.2021
 */
public abstract class FilterCrudService<T extends IdHolder<ID>, R extends PageableRepository<T, ID>, ID, F> extends
        CrudService<T, R, ID> implements FilterPageableService<T, F> {

}
