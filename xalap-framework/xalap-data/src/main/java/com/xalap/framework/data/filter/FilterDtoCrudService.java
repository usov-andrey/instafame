/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.filter;

import com.xalap.framework.data.dto.DtoCrudService;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.page.FilterPageableService;
import com.xalap.framework.domain.page.request.PageRequest;

import java.util.List;

/**
 * DtoCrudService + возможность фильтрации
 *
 * @author Usov Andrey
 * @since 14.05.2021
 */
public abstract class FilterDtoCrudService<D extends IdHolder<I>, T extends IdHolder<I>, S extends FilterCrudService<T, ?, I, F>, I, F>
        extends DtoCrudService<D, T, S, I> implements FilterPageableService<D, F> {

    @Override
    public List<D> findPage(PageRequest pageable, F filter) {
        return toDto(service.findPage(pageable, filter));
    }

    @Override
    public long count(F filter) {
        return service.count(filter);
    }
}
