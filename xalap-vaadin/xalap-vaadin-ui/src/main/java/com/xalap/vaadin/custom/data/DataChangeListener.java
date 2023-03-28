/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.data;

import java.io.Serializable;

/**
 * @author Усов Андрей
 * @since 10/05/2019
 */
public interface DataChangeListener<T> extends Serializable {

    void onDataChange(T value);
}
