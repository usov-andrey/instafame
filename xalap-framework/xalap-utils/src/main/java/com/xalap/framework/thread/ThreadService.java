/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.thread;

import com.xalap.framework.utils.DateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Разные полезные функции с потоками
 */
@Service
public class ThreadService {

    private static final Logger log = LoggerFactory.getLogger(ThreadService.class);

    private int threadCounter = 0;

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void internalSleepUntil(Long time) {
        while (time > System.currentTimeMillis()) {
            long sleepTime = (time - System.currentTimeMillis());
            if (sleepTime > 0) {
                sleep(sleepTime);
            }
        }
    }

    public ThreadFactory getThreadFactory(String threadNamePrefix) {
        return (target) -> new Thread(target, threadNamePrefix + (threadCounter++));
    }

    public Thread thread(RunnableNamed runnableNamed) {
        Thread thread = new Thread(() -> {
            try {
                runnableNamed.run();
            } catch (Exception e) {
                log.error("Error", e);
                throw e;
            }
        }, runnableNamed.getName());
        thread.start();
        return thread;
    }

    public Thread thread(Runnable runnable, String threadName) {
        return thread(new RunnableNamed(runnable, threadName));
    }

    private String dateTime(int repeatAfterSec) {
        return DateHelper.getDateTime(new Date(nextRunTime(repeatAfterSec)));
    }

    /**
     * @param repeatAfterSec - Через сколько секунд после успешного выполнения запускать еще раз?
     */
    public void runAndRepeat(Supplier<Thread> threadSupplier, int repeatAfterSec, String runnerThreadName) {
        runAndRepeat(threadSupplier, repeatAfterSec, () -> true, thread -> log.debug("Wait repeat thread:" + thread.getName() + " until " + dateTime(repeatAfterSec)), runnerThreadName);
    }

    public void runAndRepeat(Supplier<Thread> threadSupplier, int repeatAfterSec, Supplier<Boolean> repeatFunction,
                             Consumer<Thread> sleepFunction, String runnerThreadName) {
        thread(() -> {
            while (repeatFunction.get()) {
                Thread thread = threadSupplier.get();
                waitThread(thread);
                sleepFunction.accept(thread);
                internalSleepUntil(nextRunTime(repeatAfterSec));
            }
        }, runnerThreadName);
    }

    /**
     * Выполняем repeatFunction пока она не вернет false
     */
    public void runAndRepeat( int repeatAfterSec, Supplier<Boolean> repeatFunction, String runnerThreadName) {
        thread(() -> {
            while (repeatFunction.get()) {
                internalSleepUntil(nextRunTime(repeatAfterSec));
            }
        }, runnerThreadName);
    }

    /**
     * Запускаем поток и повторяем через каждую секунду, в лог информацию о повторах не выводим
     */
    public void runAndRepeat(Supplier<Thread> threadSupplier, String runnerThreadName) {
        runAndRepeat(threadSupplier, 1, () -> true, thread -> {}, runnerThreadName);
    }

    /**
     * Запускаем поток и повторяем через каждую секунду, в лог информацию о повторах не выводим
     */
    public void runAndRepeat(Runnable runnable, String threadName) {
        runAndRepeat(() -> thread(runnable, threadName), "Runner-" + threadName);
    }

    /**
     * Запускаем поток и повторяем через каждую секунду, в лог информацию о повторах не выводим
     */
    public void runAndRepeat(Runnable runnable, int repeatAfterSec, String threadName) {
        runAndRepeat(() -> thread(runnable, threadName), repeatAfterSec, "Runner-" + threadName);
    }

    private long nextRunTime(int repeatAfterSec) {
        return System.currentTimeMillis() + repeatAfterSec * 1000;
    }

    public void waitThread(Thread thread) {
        try {
            thread.join();
        } catch (Exception e) {
            throw new IllegalStateException("Error on thread.join", e);
        }
    }

}
