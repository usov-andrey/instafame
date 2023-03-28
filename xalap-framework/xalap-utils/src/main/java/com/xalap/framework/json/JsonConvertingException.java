/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.json;

/**
 * Ошибка в случае конвертации из json
 * @deprecated Нужно использовать стандартное JsonProcessingException
 *
 * @author Usov Andrey
 * @since 2020-08-27
 */
@Deprecated
public class JsonConvertingException extends RuntimeException {

    public JsonConvertingException(String message, Throwable cause) {
        super(message, cause);
    }
}
