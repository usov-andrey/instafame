/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.exception;

import org.slf4j.Logger;

/**
 * Исключение в случае временных сетевых проблем
 * Позволяет в самом исключении задавать логику, как обрабатывать это исключение
 *
 * @author Усов Андрей
 * @since 28/06/2019
 */
public class ServiceTemporaryException extends Exception {

    public ServiceTemporaryException() {
    }

    public ServiceTemporaryException(String error) {
        super(error);
    }

    public ServiceTemporaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceTemporaryException(ServiceWrongResponseException e) {
        super(e.getMessage(), e);
    }

    public void log(Logger log, String s) {
        log.warn(s, this);
    }

}
