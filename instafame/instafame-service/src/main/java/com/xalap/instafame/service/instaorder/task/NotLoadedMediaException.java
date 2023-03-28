/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

/**
 * @author Усов Андрей
 * @since 01/09/2019
 */
public class NotLoadedMediaException extends RuntimeException {

    public NotLoadedMediaException(String message) {
        super(message);
    }

}
