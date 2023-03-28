/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.holder;

/**
 * Если необходимо бины поддерживать в каком-то порядке
 *
 * @author Усов Андрей
 * @since 13/06/2019
 */
public interface OrderHolder {

    long getOrderIndex();

    void setOrderIndex(long orderIndex);

}
