/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.logging;

import com.xalap.uaa.service.utils.SecurityUtils;
import org.slf4j.MDC;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Usov Andrey
 * @since 2020-04-24
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CurrentUserLoggingFilter extends OncePerRequestFilter implements ApplicationListener<AuthorizedEvent> {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Информация об авторизованном пользователе подставляется
        MDC.put("user", "guest");
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void onApplicationEvent(AuthorizedEvent event) {
        MDC.put("user", SecurityUtils.extractPrincipal(event.getAuthentication()));
    }
}
