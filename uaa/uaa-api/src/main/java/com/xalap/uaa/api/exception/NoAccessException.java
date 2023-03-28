/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.exception;

/**
 * @author Usov Andrey
 * @since 2020-04-17
 */
public class NoAccessException extends RuntimeException {

    public NoAccessException(String message) {
        super(message);
    }
}
