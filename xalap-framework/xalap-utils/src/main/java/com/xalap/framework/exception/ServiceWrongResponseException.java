/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.exception;

/**
 * Ошибка, когда мы получили от внешнего сервиса ответ, который мы не ожидаем
 *
 * @author Usov Andrey
 * @since 2020-07-24
 */
public class ServiceWrongResponseException extends RuntimeException {

    private final String url;
    private final String response;

    public ServiceWrongResponseException(String message, Throwable cause, String url, String response) {
        super(message, cause);
        this.url = url;
        this.response = response;
    }

    public String getUrl() {
        return url;
    }

    public String getResponse() {
        return response;
    }

}
