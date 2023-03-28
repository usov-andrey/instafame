/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Пара значений
 *
 * @author Usov Andrey
 * @since 2020-10-15
 */
public final class Pair<S, T> {
    private final S first;
    private final T second;

    public Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    public static <S, T> Pair<S, T> of(S first, T second) {
        return new Pair<>(first, second);
    }

    public static <S, T> Collector<Pair<S, T>, ?, Map<S, T>> toMap() {
        return Collectors.toMap(Pair::getFirst, Pair::getSecond);
    }

    public S getFirst() {
        return this.first;
    }

    public T getSecond() {
        return this.second;
    }
}
