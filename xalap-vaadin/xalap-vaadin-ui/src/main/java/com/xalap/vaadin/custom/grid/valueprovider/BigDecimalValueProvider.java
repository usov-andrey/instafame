/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.valueprovider;

import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author Usov Andrey
 * @since 2020-04-22
 */
public class BigDecimalValueProvider<T> implements ValueProvider<T, String> {

    private final SerializableFunction<T, BigDecimal> function;

    public BigDecimalValueProvider(SerializableFunction<T, BigDecimal> function) {
        this.function = function;
    }

    @Override
    public String apply(T t) {
        BigDecimal value = function.apply(t);
        if (value == null) {
            return "";
        }
        return getFormat().format(value);
    }

    private DecimalFormat getFormat() {
        return new DecimalFormat("###,###.00", DecimalFormatSymbols.getInstance(new Locale("ru")));
    }

}
