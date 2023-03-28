/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.integration.lock;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Блокировка на уровне базы
 *
 * @author Usov Andrey
 * @since 2020-03-24
 */
@Service
@AllArgsConstructor
public class LockService {

    private static final Logger log = LoggerFactory.getLogger(LockService.class);
    public static final int DEFAULT_TIMEOUT_MIN = 5;

    private final LockRegistry lockRegistry;
    private final TryLockService tryLockService;

    /**
     * Выполнить задачу, только если она сейчас не выполняется
     */
    public void runIfNoLock(String lockKey, Runnable runnable) {
        withLock(lockKey, 0, TimeUnit.MINUTES, runnable);
    }

    /**
     * Ожидаем снятия блокировки 5 минут
     */
    public <V> V withLock(String lockKey, Callable<V> callable) throws Exception {
        return withLock(lockKey, DEFAULT_TIMEOUT_MIN, TimeUnit.MINUTES, callable);
    }

    /**
     * Ожидаем снятия блокировки 5 минут
     */
    public void withLock(String lockKey, Runnable runnable) {
        if (!withLock(lockKey, DEFAULT_TIMEOUT_MIN, TimeUnit.MINUTES, runnable)) {
            throw new CannotAcquireLockException("Cannot acquire lock: " + lockKey);
        }
    }

    private boolean withLock(String lockKey, long time, TimeUnit unit, Runnable runnable) {
        Lock lock = lockRegistry.obtain(lockKey);
        if (tryLockService.tryLock(lock, time, unit)) {
            try {
                runnable.run();
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }

    private <V> V withLock(String lockKey, long time, TimeUnit unit, Callable<V> callable) throws Exception {
        Lock lock = lockRegistry.obtain(lockKey);
        if (tryLockService.tryLock(lock, time, unit)) {
            try {
                return callable.call();
            } finally {
                lock.unlock();
            }
        }
        throw new CannotAcquireLockException("Cannot acquire lock: " + lockKey);
    }

}
