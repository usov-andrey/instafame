/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.custom;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.ErrorHandler;
import com.xalap.framework.exception.ClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Usov Andrey
 * @since 2020-03-27
 */
@Service
public class ErrorHandlerProvider {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlerProvider.class);

    public ErrorHandler get() {
        return (ErrorHandler) event -> {
            Throwable throwable = event.getThrowable();
            if (throwable.getClass().getName().equals("org.apache.catalina.connector.ClientAbortException")) {
                log.info("Client abort", throwable);
                return;
            }
            if (throwable instanceof ClientErrorException) {
                log.warn(throwable.getMessage(), throwable);
                Notification.show(throwable.getMessage());
                return;
            }
            log.error("Uncaught UI exception", throwable);
            Notification.show(
                    "We are sorry, but an internal error occurred");
        };
    }
}
