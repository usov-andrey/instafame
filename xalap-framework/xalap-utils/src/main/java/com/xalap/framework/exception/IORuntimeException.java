/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.exception;

import java.io.IOException;

/**
 * IOException, но не checked
 *
 * @author Usov Andrey
 * @since 16.12.2020
 */
public class IORuntimeException extends RuntimeException {

    public IORuntimeException(String message, IOException e) {
        super(message, e);
    }
}
