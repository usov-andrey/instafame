/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.integration.lock;

/**
 * Если в текущий момент уже происходит выполнение, то runnable не выполняется
 *
 * @author Usov Andrey
 * @since 2020-03-27
 */
public class LockableRunnable implements Runnable {

    private final Runnable runnable;
    private final String lockKey;
    private final LockService lockService;

    public LockableRunnable(Runnable runnable, String lockKey, LockService lockService) {
        this.runnable = runnable;
        this.lockKey = lockKey;
        this.lockService = lockService;
    }

    @Override
    public void run() {
        lockService.runIfNoLock(lockKey, runnable);
    }
}
