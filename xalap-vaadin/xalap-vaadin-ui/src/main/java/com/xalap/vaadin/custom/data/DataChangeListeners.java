/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.data;

import java.util.HashSet;

/**
 * @author Усов Андрей
 * @since 08/06/2019
 */
public class DataChangeListeners<T> {

    private final HashSet<DataChangeListener<T>> listeners = new HashSet<>();

    public void addListener(DataChangeListener<T> listener) {
        listeners.add(listener);
    }

    protected void onDataChange(T value) {
        for (DataChangeListener<T> listener : listeners) {
            listener.onDataChange(value);
        }
    }
}
