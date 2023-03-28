/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.integration.lock;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Иногда встречаются ошибки вида:
 * org.springframework.dao.CannotAcquireLockException: Failed to lock mutex at 6786f3c6-2fbf-3021-a94f-6e51cc07fe3c; nested exception is org.springframework.orm.jpa.JpaSystemException: Unable to commit against JDBC Connection; nested exception is org.hibernate.TransactionException: Unable to commit against JDBC Connection
 * Caused by: org.postgresql.util.PSQLException: ОШИБКА: не удалось сериализовать доступ из-за зависимостей чтения/записи между транзакциями
 * Detail: Reason code: Canceled on identification as a pivot, during commit attempt.
 * Hint: Транзакция может завершиться успешно при следующей попытке.
 * <p>
 * Поэтому нужно в случае получения этого исключения пробовать повторить.
 *
 * @author Usov Andrey
 * @since 2020-03-27
 */
@Service
public class TryLockService {

    /**
     * После успешного вызова этго метода обязательно должен быть блок try finally {lock.unlock();}
     */
    @Retryable(value = {CannotAcquireLockException.class}, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public boolean tryLock(Lock lock, long time, TimeUnit unit) {
        try {
            return lock.tryLock(time, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("InteruptedException", e);
        }
    }
}
