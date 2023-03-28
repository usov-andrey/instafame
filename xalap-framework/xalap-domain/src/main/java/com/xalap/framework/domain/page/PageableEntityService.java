/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.page;


import com.xalap.framework.domain.entity.GetEntityService;
import com.xalap.framework.domain.holder.IdHolder;

/**
 * @author Usov Andrey
 * @since 2020-04-22
 */
public interface PageableEntityService<B extends IdHolder<I>, I>
        extends GetEntityService<B, I>, PageableService<B> {

}
