/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Усов Андрей
 * @since 18/04/2019
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {

    String value();
}
