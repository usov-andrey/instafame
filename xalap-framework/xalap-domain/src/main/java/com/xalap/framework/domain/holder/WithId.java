/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.holder;

import java.io.Serializable;

/**
 * Обьект с идентификатором
 *
 * @author Usov Andrey
 * @since 27.03.2022
 */
public interface WithId extends Serializable {

    /**
     * @return пустой ключ или нет
     */
    boolean idNotNull();
}
