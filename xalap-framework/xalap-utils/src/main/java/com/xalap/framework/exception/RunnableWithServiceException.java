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
public interface RunnableWithServiceException {

    static void run(RunnableWithServiceException runnable) {
        try {
            runnable.run();
        } catch (ServiceTemporaryException e) {
            throw new IllegalStateException("Error on run runnable: " + runnable, e);
        }
    }

    void run() throws ServiceTemporaryException;

}
