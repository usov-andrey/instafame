/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.thread;

/**
 *
 * @author Усов Андрей
 * @since 25.05.17
 */
public class RunnableNamed implements Runnable {

    private final String name;
    private final Runnable delegate;

    public RunnableNamed(Runnable delegate, String name) {
        this.name = name;
        this.delegate = delegate;
    }

    @Override
    public void run() {
        delegate.run();
    }

    public String getName() {
        return name;
    }
}
