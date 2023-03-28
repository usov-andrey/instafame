/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.direct;

/**
 * @author Усов Андрей
 * @since 01/06/2019
 */
public class DirectThreadResponse extends StatusResponse {

    private DirectThread thread;

    public DirectThread getThread() {
        return thread;
    }

    public void setThread(DirectThread thread) {
        this.thread = thread;
    }

}
