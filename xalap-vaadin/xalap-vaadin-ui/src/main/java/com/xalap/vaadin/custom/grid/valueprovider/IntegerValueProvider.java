/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.valueprovider;

import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
public class IntegerValueProvider<T> implements ValueProvider<T, String> {

    private final SerializableFunction<T, Integer> function;

    public IntegerValueProvider(SerializableFunction<T, Integer> function) {
        this.function = function;
    }

    public static <T> IntegerValueProvider<T> fromLong(SerializableFunction<T, Long> function) {
        return new IntegerValueProvider<>(t -> function.apply(t).intValue());
    }

    @Override
    public String apply(T t) {
        Integer value = function.apply(t);
        return value != null ? value.toString() : "";
    }

}