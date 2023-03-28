/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import java.util.function.Predicate;

/**
 * @author Усов Андрей
 * @since 26/06/2019
 */
public class ComboFilterValue<B> {

    private final String value;
    private final Predicate<B> predicate;

    public ComboFilterValue(String value, Predicate<B> predicate) {
        this.value = value;
        this.predicate = predicate;
    }

    public String getValue() {
        return value;
    }

    public Predicate<B> getPredicate() {
        return predicate;
    }

    @Override
    public String toString() {
        return "ComboFilterValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
