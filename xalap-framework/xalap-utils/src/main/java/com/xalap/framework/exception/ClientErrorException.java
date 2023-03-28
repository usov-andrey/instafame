/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.exception;

/**
 * Исключение в случае, неверных действий пользователя
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
public class ClientErrorException extends RuntimeException {

    public ClientErrorException(String message) {
        super(message);
    }

}
