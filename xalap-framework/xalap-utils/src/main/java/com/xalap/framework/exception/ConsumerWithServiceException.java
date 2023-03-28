/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.exception;

/**
 * @author Usov Andrey
 * @since 2020-08-29
 */
@FunctionalInterface
public interface ConsumerWithServiceException<T> {

    void accept(T t) throws ServiceTemporaryException;

}
