/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Локализованное значение
 *
 * @author Usov Andrey
 * @since 30.04.2021
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface I18NCaption {

    String messageKey();
}
