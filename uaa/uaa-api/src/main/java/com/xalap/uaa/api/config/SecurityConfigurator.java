/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Регистрация внешних обработчиков конфигурации security
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
@Service
public class SecurityConfigurator {

    private final List<Consumer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry>>
            consumerUrlRegistryList = new ArrayList<>();
    private final List<Consumer<WebSecurity.IgnoredRequestConfigurer>> consumerRequestConfigurerList = new ArrayList<>();
    private final List<String> corsAllowedOrigins = new ArrayList<>();
    private final List<Consumer<CsrfConfigurer<HttpSecurity>>> csrfConsumerList = new ArrayList<>();

    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        consumerUrlRegistryList.forEach(consumer -> consumer.accept(registry));
    }

    public SecurityConfigurator addUrlRegistry(Consumer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> consumer) {
        consumerUrlRegistryList.add(consumer);
        return this;
    }

    public void config(WebSecurity.IgnoredRequestConfigurer ignoredRequestConfigurer) {
        consumerRequestConfigurerList.forEach(consumer -> consumer.accept(ignoredRequestConfigurer));
    }

    public SecurityConfigurator addIgnoring(Consumer<WebSecurity.IgnoredRequestConfigurer> consumer) {
        consumerRequestConfigurerList.add(consumer);
        return this;
    }

    public SecurityConfigurator addAllowedOrigins(String... urls) {
        corsAllowedOrigins.addAll(Arrays.asList(urls));
        return this;
    }

    public List<String> getCorsAllowedOrigins() {
        return corsAllowedOrigins;
    }

    public void config(CsrfConfigurer<HttpSecurity> csrf) {
        csrfConsumerList.forEach(csrfConfigurerConsumer -> csrfConfigurerConsumer.accept(csrf));
    }

    public SecurityConfigurator addCsrfConfigurer(Consumer<CsrfConfigurer<HttpSecurity>> consumer) {
        csrfConsumerList.add(consumer);
        return this;
    }

    /**
     * Даем анонимный доступ ко всем функциях в Controller
     */
    public void addAnonymousAccess(String controllerName) {
        String controllerUrl = "/" + controllerName + "/**";
        addUrlRegistry(consumer -> consumer
                      //Даем доступ без авторизации
                        .antMatchers(controllerUrl).permitAll())
                .addCsrfConfigurer(csrf -> csrf.ignoringAntMatchers(controllerUrl));
    }
}
