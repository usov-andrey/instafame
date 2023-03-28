/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.web.logging;

import com.xalap.framework.utils.WebHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Простейшее логирование всех
 *
 *
 * http:
 *     encoding:
 *       charset: UTF-8 # the encoding of HTTP requests/responses
 *       enabled: true # enable http encoding support
 *       force: true # force the configured encoding
 *
 * @author Усов Андрей
 * @since 27/06/2019
 */
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        boolean isResource = false;//StringHelper.endsWithAny(uri, ".html", ".js", ".css", ".png", ".jpeg");
        long startTime = System.currentTimeMillis();
        if (!isResource) {
            boolean isPost = "POST".equalsIgnoreCase(req.getMethod());
            if (isPost) {
                request = new MultiReadHttpServletRequest(req);
                req = (HttpServletRequest) request;
            }
            log.debug("Request: {} {} Params: {} {}", req.getMethod(), req.getRequestURI(), WebHelper.getRequestForLog(req),
                    isPost ? "Body: " + WebHelper.getRequestBodyForLog(req) : ""
            );
        }
        chain.doFilter(request, response);
        HttpServletResponse resp = (HttpServletResponse) response;
        if (resp.getStatus() == HttpServletResponse.SC_MOVED_TEMPORARILY || resp.getHeader("location") != null) {
            log.debug("Redirect to " + resp.getHeader("location"));
        } else if (resp.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            log.warn("Not found:" + uri);
        }
        if (!isResource) {
            long procTime = System.currentTimeMillis() - startTime;
            log.debug(
                    "Response :{} ms",
                    procTime);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    // other methods
}
