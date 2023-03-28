/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.integration.lock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * Работаем с блокировками через jdbc
 *
 * @author Usov Andrey
 * @since 2020-03-24
 */
@EnableRetry
@Configuration
public class JDBCLockConfiguration {

    @Bean
    public DefaultLockRepository lockRepository(DataSource dataSource) {
        DefaultLockRepository repository = new DefaultLockRepository(dataSource) {

            /*
                Нам нужно пытаться поставить блокировку в рамках новой транзакции, чтобы timeout не мешался с таймаутом текущей
             */
            @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 30, propagation = Propagation.REQUIRES_NEW)
            @Override
            public boolean acquire(String lock) {
                return super.acquire(lock);
            }
        };
        repository.setTimeToLive(ExpiredLocksCleaner.MAX_TIME_FOR_LOCK_LIVE);
        return repository;
    }

    @Bean
    public JdbcLockRegistry lockRegistry(LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }
}
