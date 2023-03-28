/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.page;

import com.xalap.framework.domain.page.request.PageRequest;

import java.util.List;

/**
 * @author Usov Andrey
 * @since 2020-04-28
 */
public interface FilterPageableService<B, F> extends PageableService<B> {

    List<B> findPage(PageRequest pageable, F filter);

    long count(F filter);
}
