/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.entity;

import com.xalap.framework.domain.holder.IdHolder;

/**
 * Добавляется функциональность получения по id
 *
 * @author Usov Andrey
 * @since 27.03.2022
 */
public interface GetEntityService<B extends IdHolder<I>, I> extends EntityWithIdService<B> {

    /**
     * @param id идентификатор сущности
     * @return обьект сущности по идентификатору
     */
    B get(I id);

}
