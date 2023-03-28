/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.holder;

/**
 * @author Usov Andrey
 * @since 2020-05-14
 */
public interface NameHolder {

    String getName();

    default String name() {
        return getName();
    }

}
