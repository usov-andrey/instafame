/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.entity;

import java.util.List;

/**
 * Основные операции над сущностью
 *
 * @author Усов Андрей
 * @since 18/06/2019
 */
public interface EntityService<B> {

    /**
     * Сохранить
     */
    B save(B bean);

    /**
     * Удалить
     */
    void delete(B bean);

    void copy(B bean);

    @Deprecated
    List<B> getAll();

    Class<B> getBeanClass();

    B createNew();

}
