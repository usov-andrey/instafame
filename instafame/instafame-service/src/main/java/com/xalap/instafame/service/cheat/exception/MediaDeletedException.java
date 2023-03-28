/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.exception;

/**
 * @author Usov Andrey
 * @since 2020-08-15
 */
public class MediaDeletedException extends RuntimeException {

    public MediaDeletedException(String message) {
        super(message);
    }

    public MediaDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
