/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.exception;

/**
 * @author Усов Андрей
 * @since 08/07/2019
 */
public class ServiceDisabledException extends RuntimeException {

    public ServiceDisabledException(String message) {
        super(message);
    }
}
