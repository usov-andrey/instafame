/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task.async;

import com.xalap.framework.task.TaskCallable;
import com.xalap.framework.task.TaskRunner;
import com.xalap.framework.task.ThreadNameChangeCallable;
import com.xalap.framework.task.ThreadNameChangeRunnable;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Выполнение задач в отдельных локальных потоках
 *
 * @author Андрей
 * @since 24.09.15
 */
public class AsyncTaskRunner extends TaskRunner {

    private final ExecutorService executor;
    private int index = 0;

    public AsyncTaskRunner(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void run(BiConsumer<Runnable, Exception> exceptionConsumer, Runnable task) {
        executor.execute(exceptionRunnable(exceptionConsumer, task));
    }

    @Override
	public void runAndWait(BiConsumer<Runnable, Exception> exceptionConsumer, Collection<Runnable> tasks) {
        //Возможно имееет смысл перевести это на CompletableFuture
        Map<Runnable, Future<?>> futures = new HashMap<>();
        for (Runnable task : tasks) {
            futures.put(task, executor.submit(exceptionRunnable(exceptionConsumer, task)));
        }
        for (Map.Entry<Runnable, Future<?>> runnableFutureEntry : futures.entrySet()) {
            Runnable task = runnableFutureEntry.getKey();
            Future<?> future = futures.get(task);
            try {
                future.get();
            } catch (InterruptedException e) {
                log.warn("Someone outside call cancel for this task. Put breakpoint to Thread.interrupt() for know", e);
                exceptionConsumer.accept(task, e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                exceptionConsumer.accept(task, e);
            }
        }
	}

	@Override
	public <T> Collection<T> call(BiFunction<TaskCallable<T>, Exception, T> exceptionConsumer, Collection<TaskCallable<T>> tasks) {
		//Возможно имееет смысл перевести это на CompletableFuture
		Map<TaskCallable<T>, Future<T>> futures = new HashMap<>();
		for (TaskCallable<T> callable : tasks) {
            futures.put(callable, executor.submit(exceptionCallable(exceptionConsumer, callable)));
		}
		List<T> result = new ArrayList<>();
        for (TaskCallable<T> callable : futures.keySet()) {
            Future<T> future = futures.get(callable);
            try {
                result.add(future.get());
            } catch (Exception e) {
                result.add(exceptionConsumer.apply(callable, e));
            }
        }
		return result;
	}


    public <R, S> Collection<R> call(Collection<S> sourceCollection, Function<S, R> function, Function<S, String> threadName) {
        if (sourceCollection.isEmpty()) {
            return new ArrayList<>();
        }
        List<TaskCallable<R>> tasks = sourceCollection.stream()
                .map((Function<S, TaskCallable<R>>)
                        s -> new ThreadNameChangeCallable<>((TaskCallable<R>) () -> function.apply(s), getThreadName(threadName, s)))
                .collect(Collectors.toList());
        return call(tasks);
    }

    public <S> void runAndWait(Collection<S> sourceCollection, Consumer<S> consumer, Function<S, String> threadName) {
        if (sourceCollection.isEmpty()) {
            return;
        }
        List<Runnable> tasks = sourceCollection.stream()
                .map((Function<S, Runnable>)
                        s -> new ThreadNameChangeRunnable(() -> consumer.accept(s), getThreadName(threadName, s)))
                .collect(Collectors.toList());
        runAndWait(tasks);
    }

    private <S> String getThreadName(Function<S, String> threadName, S s) {
        return Thread.currentThread().getName() + "|" + threadName.apply(s) + "." + (index++);
    }

    public void run(Runnable source, String threadName) {
        run(new ThreadNameChangeRunnable(source, threadName));
    }

}
