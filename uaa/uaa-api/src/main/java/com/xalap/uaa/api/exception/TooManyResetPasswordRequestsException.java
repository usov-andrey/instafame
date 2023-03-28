/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.exception;

/**
 * Слишком частая попытка сбросить пароль
 *
 * @author Usov Andrey
 * @since 2020-04-09
 */
public class TooManyResetPasswordRequestsException extends RuntimeException {

    public TooManyResetPasswordRequestsException(String message) {
        super(message);
    }
}
