/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task;

import java.util.function.BiConsumer;

/**
 *
 * @author Усов Андрей
 * @since 15.05.2018
 */
public class ExceptionRunnable extends DelegateRunnable {

    private final BiConsumer<Runnable, Exception> exceptionConsumer;

    public ExceptionRunnable(BiConsumer<Runnable, Exception> exceptionConsumer, Runnable delegate) {
        super(delegate);
        this.exceptionConsumer = exceptionConsumer;
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (Exception e) {
            exceptionConsumer.accept(delegate, e);
        }
    }
}
