/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Usov Andrey
 * @since 2020-08-29
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class SomethingWrongException extends RuntimeException {

    public SomethingWrongException(String message) {
        super(message);
    }
}
