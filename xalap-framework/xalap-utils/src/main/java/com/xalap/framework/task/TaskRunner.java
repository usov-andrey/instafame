/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Простой запуск thread и call
 * @author Андрей
 * @since 24.09.15
 */

public class TaskRunner {

    protected static final Logger log = LoggerFactory.getLogger(TaskRunner.class);

    public void run(BiConsumer<Runnable, Exception> exceptionConsumer, Runnable task) {
        exceptionRunnable(exceptionConsumer, task).run();
    }

    protected ExceptionRunnable exceptionRunnable(BiConsumer<Runnable, Exception> exceptionConsumer, Runnable task) {
        return new ExceptionRunnable(exceptionConsumer, task);
    }

    protected BiConsumer<Runnable, Exception> logRunnableExceptionConsumer() {
        return (Runnable, e) -> {
            log.error("Error on run task:" + Runnable, e);
            throw new TaskRunException(e);
        };
    }

    /**
     * Запускаем задачу и в случае ошибки выводим в лог и кидаем исключение TaskRunException
     */
	public final void run(Runnable task) {
		run(logRunnableExceptionConsumer(), task);
	}

	public void run(BiConsumer<Runnable, Exception> exceptionConsumer, Collection<Runnable> tasks) {
        tasks.forEach((task) -> {
            run(exceptionConsumer, task);
        });
	}

    /**
     * В случае, возникновении ошибки в какой-то задаче, исключение идет наверх и остальные задачи не выполняются
     */
    public final void runAndWait(Collection<Runnable> tasks) {
        runAndWait(logRunnableExceptionConsumer(), tasks);
    }

	/**
	 * Запускаем выполнение задач и возвращаем результат, когда все задачи будут выполнены
	 */
	public void runAndWait(BiConsumer<Runnable, Exception> exceptionConsumer, Collection<Runnable> tasks) {
		run(exceptionConsumer, tasks);
	}

    /**
     * В случае, возникновении ошибки в какой-то задаче, исключение идет наверх и остальные задачи не выполняются
     */
    public final void runAndWait(Runnable... tasks) {
        runAndWait(logRunnableExceptionConsumer(), tasks);
    }

    /**
     * Запускаем выполнение задач и возвращаем результат, когда все задачи будут выполнены
     */
    public final void runAndWait(BiConsumer<Runnable, Exception> exceptionConsumer, Runnable... tasks) {
        runAndWait(exceptionConsumer, Arrays.asList(tasks));
    }

    /**
     * В случае какой либо ошибки, ошибка идет наверх
     */
    public final <T> Collection<T> call(Collection<TaskCallable<T>> tasks) {
        return call(logCallableExceptionConsumer(), tasks);
    }

	public final <T> T call(TaskCallable<T> callable) {
        return call(logCallableExceptionConsumer(), callable);
	}

    public <T> T call(BiFunction<TaskCallable<T>, Exception, T> exceptionConsumer, TaskCallable<T> callable) {
        return exceptionCallable(exceptionConsumer, callable).safeCall();
    }

    public <T> Collection<T> call(BiFunction<TaskCallable<T>, Exception, T> exceptionConsumer, Collection<TaskCallable<T>> tasks) {
        Collection<T> result = new ArrayList<>();
        for (TaskCallable<T> callable : tasks) {
            result.add(call(exceptionConsumer, callable));
        }
        return result;
    }

    protected <T> BiFunction<TaskCallable<T>, Exception, T> logCallableExceptionConsumer() {
        return (taskCallable, e) -> {
            log.error("Error on run task:" + taskCallable, e);
            throw new TaskRunException(e);
        };
    }

    protected <T> ExceptionCallable<T> exceptionCallable(BiFunction<TaskCallable<T>, Exception, T> exceptionConsumer, TaskCallable<T> task) {
        return new ExceptionCallable<>(exceptionConsumer, task);
    }

}
