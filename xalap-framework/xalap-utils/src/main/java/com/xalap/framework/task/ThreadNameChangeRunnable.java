/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task;

/**
 *
 * @author Усов Андрей
 * @since 15.05.2018
 */
public class ThreadNameChangeRunnable extends DelegateRunnable {

    private final String name;

    public ThreadNameChangeRunnable(Runnable delegate, String name) {
        super(delegate);
        this.name = name;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        String oldName = thread.getName();
        thread.setName(name);
        super.run();
        //В случае исключения имя потока не меняем обратно
        thread.setName(oldName);
    }

    @Override
    public String toString() {
        return "ThreadName:" + name + " Runnable:" + delegate.toString();
    }
}
