/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Хранение минимального и максимальных целых значений
 *
 * @author Усов Андрей
 * @since 26.05.17
 */
public class MinMax {

    private static final Random random = new SecureRandom();

    private final Integer min;
    private final Integer max;

    public MinMax(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    public static MinMax one(Integer number) {
        return new MinMax(number, number);
    }

    public static MinMax min(Integer number) {
        return new MinMax(number, Integer.MAX_VALUE);
    }

    public static MinMax max(Integer number) {
        return new MinMax(Integer.MIN_VALUE, number);
    }

    public static Boolean in(Integer count, int min, int max) {
        return min < count && max > count;
    }

    public Integer random() {
        int bound = max - min;
        return bound > 0 ? min + random.nextInt(bound) : min;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }


    public boolean in(Integer count) {
        return count != null && count < max && count > min;
    }

    @Override
    public String toString() {
        return "min=" + min +
                ", max=" + max;
    }
}
