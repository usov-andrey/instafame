/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.app;

import com.xalap.crm.service.integration.robokassa.RobokassaController;
import com.xalap.crm.service.integration.tilda.TildaController;
import com.xalap.instagram.service.search.InstagramController;
import com.xalap.uaa.api.config.SecurityConfigurator;
import org.springframework.context.annotation.Configuration;

/**
 * @author Usov Andrey
 * @since 2020-04-06
 */
@Configuration
public class InstaFameSecurityConfiguration {

    public InstaFameSecurityConfiguration(SecurityConfigurator configurator) {
        configurator
                .addUrlRegistry(consumer -> consumer
                        //Даем доступ без авторизации
                        .antMatchers(getControllerPaths()
                        ).permitAll())
                .addAllowedOrigins("https://api.instafame.ru", "https://instafame.ru", "http://instafame.xalap.com",
                        "http://crm.xalap.com")
                .addCsrfConfigurer(csrf -> csrf.ignoringAntMatchers(getControllerPaths()));
    }

    private String[] getControllerPaths() {
        return new String[]{"/" + TildaController.NAME,
                "/" + InstagramController.NAME + "/**",
                "/" + RobokassaController.NAME + "/**"};
    }

}
