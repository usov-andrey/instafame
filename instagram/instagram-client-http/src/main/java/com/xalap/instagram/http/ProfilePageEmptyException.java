/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http;

public class ProfilePageEmptyException extends RuntimeException {

    public ProfilePageEmptyException(String message) {
        super(message);
    }

    public ProfilePageEmptyException(String message, Throwable e) {
        super(message, e);
    }
}
