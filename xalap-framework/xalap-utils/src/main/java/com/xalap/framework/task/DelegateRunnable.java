/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task;

import java.io.Serializable;

/**
 *
 * @author Усов Андрей
 * @since 15.05.2018
 */
public class DelegateRunnable implements Runnable, Serializable {

    protected Runnable delegate;

    public DelegateRunnable(Runnable delegate) {
        this.delegate = delegate;
    }

    @Override
    public void run() {
        delegate.run();
    }
}
