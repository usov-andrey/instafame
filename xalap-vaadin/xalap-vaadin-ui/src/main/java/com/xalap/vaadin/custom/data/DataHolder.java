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
public class DataHolder<T extends Serializable> extends DataChangeListeners<T> implements Serializable {

    protected T value;

    public void refresh() {
        doRefresh();
        onDataChange(value);
    }

    public Runnable withRefresh(Runnable runnable) {
        return () -> {
            runnable.run();
            refresh();
        };
    }

    public T getValue() {
        if (value == null) {
            throw new IllegalStateException("Value is not initialized");
        }
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        refresh();
    }

    protected void doRefresh() {
        //В стандартной реализации ничего не делаем
    }
}
