/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Задает Caption обьекта
 * Без поддержки интернационализации
 * 
 * @author Усов Андрей
 * @since 19/01/2019
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Caption {

    String value();
}
