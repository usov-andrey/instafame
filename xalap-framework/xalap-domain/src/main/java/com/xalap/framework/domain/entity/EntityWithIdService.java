/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.entity;

import com.xalap.framework.domain.holder.WithId;

/**
 * В сущности должен быть какой-то ключ, добавляется на это проверка
 *
 * @author Usov Andrey
 * @since 2020-02-14
 */
public interface EntityWithIdService<B extends WithId> extends EntityService<B> {

    default boolean containsKey(B bean) {
        return bean.idNotNull();
    }

}
