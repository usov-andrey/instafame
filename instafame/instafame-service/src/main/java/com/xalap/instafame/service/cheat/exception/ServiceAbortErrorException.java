/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.exception;

/**
 * @author Усов Андрей
 * @since 23/07/2019
 */
public class ServiceAbortErrorException extends RuntimeException {

    public ServiceAbortErrorException(String message) {
        super(message);
    }
}
