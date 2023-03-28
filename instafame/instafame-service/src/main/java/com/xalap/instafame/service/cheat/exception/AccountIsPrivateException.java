/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.exception;

/**
 * @author Usov Andrey
 * @since 2020-04-19
 */
public class AccountIsPrivateException extends RuntimeException {

    public AccountIsPrivateException(String message) {
        super(message);
    }
}
