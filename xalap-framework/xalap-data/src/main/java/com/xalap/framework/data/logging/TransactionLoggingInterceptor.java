/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.logging;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Дополнительное логирование информации о транзакциях
 *
 * @author Usov Andrey
 * @since 2020-04-10
 */
@Component
public class TransactionLoggingInterceptor extends EmptyInterceptor {

    private final Logger log = LoggerFactory.getLogger("transaction");

    @Override
    public void afterTransactionBegin(Transaction tx) {
        super.afterTransactionBegin(tx);
        log.debug("afterBegin" + toString(tx));
    }

    private String toString(Transaction tx) {
        return "[" + tx.hashCode() + "]:" + tx.getTimeout();
    }

    @Override
    public void beforeTransactionCompletion(Transaction tx) {
        super.beforeTransactionCompletion(tx);
        log.debug("beforeCompletion" + toString(tx));
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        super.afterTransactionCompletion(tx);
        log.debug("afterCompletion" + toString(tx));
    }
}
