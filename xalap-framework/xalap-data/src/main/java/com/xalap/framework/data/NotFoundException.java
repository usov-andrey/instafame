/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data;

/**
 * Выбрасывается если обьект не найден
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
