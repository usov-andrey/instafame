/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.web.logging;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Также нужно в properties включить это отображение:
 * logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
 *
 * @author Usov Andrey
 * @since 2020-04-07
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class RequestLoggingNewFilter extends CommonsRequestLoggingFilter {

    public RequestLoggingNewFilter() {
        setIncludeQueryString(true);
        setIncludePayload(true);
        setMaxPayloadLength(10000);
        setIncludeHeaders(false);
        //setAfterMessagePrefix("REQUEST DATA : ");
    }
}
