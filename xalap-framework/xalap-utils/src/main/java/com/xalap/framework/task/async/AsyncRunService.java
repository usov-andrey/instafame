/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Запускаем Runnable в отдельном потоке
 *
 * @author Usov Andrey
 * @since 30.04.2021
 */
@Service
public class AsyncRunService {

    @Async
    public void run(Runnable runnable) {
        runnable.run();
    }
}
