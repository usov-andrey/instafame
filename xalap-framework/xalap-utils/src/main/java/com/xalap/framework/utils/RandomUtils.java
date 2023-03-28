/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

/**
 * Функции по работе со случайными элементами
 *
 * @author Усов Андрей
 * @since 06.06.17
 */
public class RandomUtils {

    private static final int DEF_COUNT = 20;

    private static final SecureRandom SECURE_RANDOM;

    static {
        SECURE_RANDOM = new SecureRandom();
        SECURE_RANDOM.nextBytes(new byte[64]);
    }

    private RandomUtils() {
    }

    public static String generateRandomAlphanumericString() {
        return RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM);
    }

    /**
     * Целове число от 0 до max, включая max
     */
    public static int nextInt(int max) {
        return SECURE_RANDOM.nextInt(max + 1);
    }

    public static boolean nextBoolean() {
        return nextInt(1) == 1;
    }

    public static double nextDouble() {
        return SECURE_RANDOM.nextDouble();
    }
}
