/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.valueprovider;

import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author Usov Andrey
 * @since 2020-04-14
 */
public class InstantValueProvider<T> implements ValueProvider<T, String> {

    private final SerializableFunction<T, Instant> function;

    public InstantValueProvider(SerializableFunction<T, Instant> function) {
        this.function = function;
    }

    @Override
    public String apply(T t) {
        Instant value = function.apply(t);
        if (value == null) {
            return "";
        }
        return dateTimeFormatter().format(value);
    }

    private DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(new Locale("ru"))
                .withZone(ZoneId.systemDefault());
    }

}
