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
public class ThreadNameChangeCallable<T> extends DelegateCallable<T> {

    private final String name;

    public ThreadNameChangeCallable(TaskCallable<T> delegate, String name) {
        super(delegate);
        this.name = name;
    }

    @Override
    public T call() throws Exception {
        Thread thread = Thread.currentThread();
        String oldName = thread.getName();
        thread.setName(name);
        T result = super.call();
        //В случае исключения имя потока не меняем обратно
        thread.setName(oldName);
        return result;
    }

    @Override
    public String toString() {
        return name + ": " + delegate.toString();
    }
}
