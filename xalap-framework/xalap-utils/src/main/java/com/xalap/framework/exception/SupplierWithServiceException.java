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
public interface SupplierWithServiceException<T> {

    static <T> T run(SupplierWithServiceException<T> supplier) {
        try {
            return supplier.get();
        } catch (ServiceTemporaryException e) {
            throw new IllegalStateException("Error on run supplier: " + supplier, e);
        }
    }

    T get() throws ServiceTemporaryException;


}
