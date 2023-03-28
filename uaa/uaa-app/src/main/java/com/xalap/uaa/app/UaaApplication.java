
/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.app;

import com.vaadin.flow.server.InitParameters;
import com.vaadin.flow.spring.SpringServlet;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.xalap.framework.data.page.repository.PageableRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Uaa Application
 * Для наполнения тестовыми данными нужно запускать с профилем initDB
 * Данные для входа находятся в {@link com.xalap.uaa.service.config.SetupDataLoader}
 */
@EnableVaadin({"com.xalap.uaa.ui"})
@SpringBootApplication(scanBasePackages = {
        "com.xalap.framework",
        //Безопасность
        "com.xalap.uaa.api",
        "com.xalap.uaa.service",

        "com.xalap.vaadin.starter",
        "com.xalap.vaadin.custom",
        "com.xalap.uaa.ui",
        "com.xalap.uaa.app"
}
)
@EntityScan(basePackages = {
        "com.xalap.uaa.service"})
@EnableJpaRepositories(basePackages = {
        "com.xalap.uaa.service",}, repositoryFactoryBeanClass = PageableRepositoryFactoryBean.class)
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableWebSecurity
public class UaaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UaaApplication.class).run(args);
    }

    @Bean
    public ServletRegistrationBean<SpringServlet> springServlet(ApplicationContext applicationContext,
                                                                @Value("${vaadin.urlMapping}") String vaadinUrlMapping) {

        SpringServlet servlet = new SpringServlet(applicationContext, false);
        ServletRegistrationBean<SpringServlet> registrationBean =
                new ServletRegistrationBean<>(servlet, vaadinUrlMapping, "/frontend/*");
        registrationBean.setLoadOnStartup(1);
        registrationBean.addInitParameter(InitParameters.SERVLET_PARAMETER_SYNC_ID_CHECK, "false");
        return registrationBean;
    }

}
