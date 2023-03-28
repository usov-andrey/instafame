/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.valueprovider;

import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.framework.utils.StringHelper;

/**
 * @author Усов Андрей
 * @since 30/04/2019
 */
public class DoubleValueProvider<T> implements ValueProvider<T, String> {

    private final SerializableFunction<T, Double> function;

    public DoubleValueProvider(SerializableFunction<T, Double> function) {
        this.function = function;
    }

    @Override
    public String apply(T t) {
        Double value = function.apply(t);
        return StringHelper.toString(value);
    }

}
