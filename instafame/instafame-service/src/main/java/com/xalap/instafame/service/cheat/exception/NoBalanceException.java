/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.exception;

/**
 * @author Усов Андрей
 * @since 28/06/2019
 */
public class NoBalanceException extends RuntimeException {

    public NoBalanceException(String serviceName) {
        super("Не хватает денег на балансе:" + serviceName);
    }
}
