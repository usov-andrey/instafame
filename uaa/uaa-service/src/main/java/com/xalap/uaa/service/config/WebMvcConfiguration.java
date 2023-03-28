/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.config;

import com.xalap.uaa.api.config.SecurityConfigurator;
import com.xalap.uaa.api.web.UaaHtmlPage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация MVC
 *
 * @author Usov Andrey
 * @since 2020-03-31
 */
@Component
@AllArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(WebMvcConfiguration.class);

    private final SecurityConfigurator securityConfigurator;
    private final SecurityProperties properties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        for (UaaHtmlPage htmlPage : UaaHtmlPage.values()) {
            String htmlPageName = htmlPage.getHtmlFileName();
            registry.addViewController("/" + htmlPageName).setViewName(htmlPageName);
        }
        registry.addViewController("/" + properties.getLogin().getPage());
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (log.isDebugEnabled()) {
            log.debug("get from config: {}", securityConfigurator.getCorsAllowedOrigins());
        }
        registry.addMapping("/**").allowedOrigins(securityConfigurator.getCorsAllowedOrigins().toArray(new String[0]));
    }

}
