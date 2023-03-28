
/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.starter;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.xalap.vaadin.starter.custom.MenuCreator;
import com.xalap.vaadin.starter.custom.StarterMenu;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * Стартовое приложение vaadin
 */
@SpringBootApplication(scanBasePackages = {"com.xalap.vaadin"})
@EnableVaadin("com.xalap.vaadin.starter")
public class VaadinStarterApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(VaadinStarterApplication.class).run(args);
    }

    @Bean
    public StarterMenu starterMenu(MenuCreator menuCreator) {
        return new StarterMenu(menuCreator);
    }
}
