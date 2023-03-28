/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.integration.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Очищаем устаревшие блокировки в базе
 *
 * @author Usov Andrey
 * @since 2020-03-24
 */
@Service
public class ExpiredLocksCleaner {

    public static final int MAX_TIME_FOR_LOCK_LIVE = 1000 * 60 * 30;//30 minutes

    @Autowired
    private ExpirableLockRegistry lockRegistry;

    @Scheduled(fixedDelay = MAX_TIME_FOR_LOCK_LIVE)
    public void cleanObsolete() {
        lockRegistry.expireUnusedOlderThan(MAX_TIME_FOR_LOCK_LIVE);//that are not currently locked
    }

}
