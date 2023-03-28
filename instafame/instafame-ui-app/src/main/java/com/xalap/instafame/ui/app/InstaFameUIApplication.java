/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.app;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.xalap.framework.data.page.repository.PageableRepositoryFactoryBean;
import com.xalap.instagram.api.EmptyInstagramUserApi;
import com.xalap.instagram.api.InstagramUserApi;
import com.xalap.uaa.service.config.SetupDataLoader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.xalap.uaa.api.Role.ROLE_ADMIN;

@EnableVaadin({"com.xalap.uaa.ui", "com.xalap.instafame.ui", "com.xalap.crm.ui", "com.xalap.instagram.ui",
        "com.xalap.payment.ui",
        "com.xalap.wow"})
@SpringBootApplication(scanBasePackages = {
        "com.xalap.framework",
        "com.xalap.instagram.api",
        "com.xalap.instagram.http",
        "com.xalap.instagram.service",
        "com.xalap.instagram.direct.service",
        "com.xalap.crm.service",
        "com.xalap.instafame.service",
        "com.xalap.uaa",
        "com.xalap.payment",

        //ui в такой последовательности, чтобы корректно сформировалось menu
        "com.xalap.instafame.ui",
        "com.xalap.crm.ui",
        "com.xalap.instagram.ui",
        "com.xalap.vaadin.starter",
        "com.xalap.vaadin.custom",
        "com.xalap.uaa.ui", "com.xalap.wow"
}
)
@EntityScan(basePackages = {
        "com.xalap.instagram.service",
        "com.xalap.instagram.direct.service",
        "com.xalap.crm.service",
        "com.xalap.instafame.service",
        "com.xalap.uaa.service",
        "com.xalap.payment", "com.xalap.wow"})
@EnableJpaRepositories(basePackages = {
        "com.xalap.instagram.service",
        "com.xalap.instagram.direct.service",
        "com.xalap.crm.service",
        "com.xalap.instafame.service",
        "com.xalap.uaa.service",
        "com.xalap.payment", "com.xalap.wow"}, repositoryFactoryBeanClass = PageableRepositoryFactoryBean.class)
@EnableMongoRepositories(basePackages = {"com.xalap.wow"})
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableIntegration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableWebSecurity
public class InstaFameUIApplication extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        new SpringApplicationBuilder(InstaFameUIApplication.class).run(args);
    }

    @Bean
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(30);
    }

    @Bean
    public SetupDataLoader defaultUsersCreator() {
        return new SetupDataLoader() {

            @Override
            protected void createUsers() {
                createUserIfNotFound("admin@instafame.ru", "InstaFame Admin", "instafameadmin", "Nb^7zv*9a", ROLE_ADMIN);
            }
        };
    }

    @Bean
    public InstagramUserApi instagramUserApi() {
        return new EmptyInstagramUserApi();
    }

}
