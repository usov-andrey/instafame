/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task;

/**
 *
 * @author Усов Андрей
 * @since 24.05.2018
 */
public class DelegateCallable<T> implements TaskCallable<T> {

    protected TaskCallable<T> delegate;

    public DelegateCallable(TaskCallable<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T call() throws Exception {
        return delegate.call();
    }
}
