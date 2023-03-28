/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.holder;

/**
 * Хранилище для идентификатора
 *
 * @author Usov Andrey
 * @since 29/01/2020
 */
public interface IdHolder<T> extends WithId {

    String COLUMN_ID = "id";

    T getId();

    void setId(T id);

    default String idToString() {
        return idNotNull() ? getId().toString() : "";
    }

    default boolean idNotNull() {
        return getId() != null;
    }

}
