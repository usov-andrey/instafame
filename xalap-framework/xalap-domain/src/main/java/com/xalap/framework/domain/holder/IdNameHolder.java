/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.holder;

/**
 *
 * @author Усов Андрей
 * @since 25.07.2018
 */
public interface IdNameHolder<T extends Comparable<T>> extends IdHolderWithName<T> {

    void setName(String name);
}
