/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task;

import java.util.function.BiFunction;

/**
 *
 * @author Усов Андрей
 * @since 15.05.2018
 */
public class ExceptionCallable<V> extends DelegateCallable<V> {

    private final BiFunction<TaskCallable<V>, Exception, V> exceptionConsumer;

    public ExceptionCallable(BiFunction<TaskCallable<V>, Exception, V> exceptionConsumer, TaskCallable<V> delegate) {
        super(delegate);
        this.exceptionConsumer = exceptionConsumer;
    }

    @Override
    public V call() throws Exception {
        try {
            return delegate.call();
        } catch (Exception e) {
            return exceptionConsumer.apply(delegate, e);
        }
    }

    public V safeCall() {
        try {
            return call();
        } catch (Exception e) {
            return exceptionConsumer.apply(delegate, e);
        }
    }
}
