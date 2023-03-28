/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.ui.security;

import com.xalap.uaa.api.config.SecurityConfigurator;
import com.xalap.vaadin.custom.VaadinWebContext;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация настройки безопасности для vaadin
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
@Configuration
public class VaadinSecurityConfiguration {

    public VaadinSecurityConfiguration(VaadinWebContext vaadinWebContext, SecurityConfigurator configurator) {
        //Разрешаем доступ ко всем post запросах vaadin
        configurator
                .addUrlRegistry(registry -> registry.requestMatchers(VaadinSecurityUtils::isFrameworkInternalRequest)
                        .permitAll())
                .addIgnoring(ignoring -> ignoring.antMatchers(
                        // Vaadin Flow static resources
                        ignoringAntMatchers(vaadinWebContext)
                ))
                .addCsrfConfigurer(csrf ->
                        csrf.ignoringRequestMatchers(VaadinSecurityUtils::isFrameworkInternalRequest)
                                .ignoringAntMatchers(ignoringAntMatchers(vaadinWebContext))
                );
    }

    private String[] ignoringAntMatchers(VaadinWebContext vaadinWebContext) {
        String vaadinWebContextValue = vaadinWebContext.get();
        return new String[]{vaadinWebContextValue + "VAADIN/**",
                "/VAADIN/**",

                // web application manifest
                vaadinWebContextValue + "manifest.webmanifest",
                vaadinWebContextValue + "sw.js",
                "/sw.js", //Также запросы приходят и на корневой контекст
                vaadinWebContextValue + "offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**"};
    }

}
