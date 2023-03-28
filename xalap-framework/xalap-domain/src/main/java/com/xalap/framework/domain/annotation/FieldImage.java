/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Задает картинку для поля
 * Значение нужно вводить в upperCase
 *
 * @author Усов Андрей
 * @since 19/01/2019
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldImage {

    String value();
}
