/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.valueprovider;

import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.framework.utils.DateHelper;

import java.util.Date;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
public class DateValueProvider<T> implements ValueProvider<T, String> {

    private final SerializableFunction<T, Date> function;

    public DateValueProvider(SerializableFunction<T, Date> function) {
        this.function = function;
    }

    @Override
    public String apply(T t) {
        Date value = function.apply(t);
        return DateHelper.getDate(value);
    }

}
