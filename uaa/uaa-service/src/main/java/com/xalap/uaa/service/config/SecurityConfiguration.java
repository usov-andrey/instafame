/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.config;

import com.xalap.framework.web.url.UrlManager;
import com.xalap.uaa.api.config.SecurityConfigurator;
import com.xalap.uaa.api.web.UaaHtmlPage;
import com.xalap.uaa.service.account.AccountController;
import com.xalap.uaa.service.config.login.LoginMode;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.firewall.HttpStatusRequestRejectedHandler;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;

/**
 * Настройки безопасности
 *
 * @author Usov Andrey
 * @since 2020-03-31
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String REM_ME = "RemMe";
    private final UrlManager urlManager;
    private final SecurityConfigurator configurator;
    private final UserDetailsService userDetailsService;
    private final SecurityProperties properties;

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    @Profile("initDB")
    public SetupDataLoader testData() {
        return new SetupDataLoader();
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer
                = http
                /*
                .ignoringAntMatchers(loginUrl())
                .ignoringAntMatchers()

        http.csrf().ignoringAntMatchers("/connect/**")*/

                // Register our CustomRequestCache, that saves unauthorized access attempts, so
                // the user is redirected after login.
                //.requestCache().requestCache(getRequestCache()).and()
                .requestCache().disable()

                // Restrict access to our application.
                .authorizeRequests();

        configurator.config(configurer);

        CsrfConfigurer<HttpSecurity> csrf = http.csrf();
        csrf.ignoringAntMatchers("/logout", loginUrl(), "/" + AccountController.NAME + "/**");
        configurator.config(csrf);

        configurer

                .antMatchers("/" + AccountController.NAME + "/**").permitAll()

                // Allow all requests by logged in users.
                .anyRequest().authenticated()
                //Добавляем вывод в лог текущего пользователя
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(
                            O fsi) {
                        fsi.setPublishAuthorizationSuccess(true);
                        return fsi;
                    }
                });

        configureLogin(configurer);

        configurer

                // Configure logout
                .and().logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl(logoutUrl())
                // Configure remember me cookie
                .and().rememberMe().key(REM_ME);
    }

    private void configureLogin(
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer)
            throws Exception {
        AbstractAuthenticationFilterConfigurer<HttpSecurity, ?, ?> loginConfigurer = loginConfigurer(configurer.and());
        loginConfigurer
                .permitAll()
                .successHandler(getSuccessHandler())
                .failureHandler(getAuthenticationFailureHandler());
    }

    private AbstractAuthenticationFilterConfigurer<HttpSecurity, ?, ?> loginConfigurer(HttpSecurity security) throws Exception {
        if (properties.getLogin().getMode() == LoginMode.OAUTH_2) {
            return security.oauth2Login()
                    .loginPage(loginUrl());
        } else {
            return security
                    .formLogin()
                    .loginPage(loginUrl())
                    .loginProcessingUrl(loginUrl());
        }
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices(REM_ME, userDetailsService);
    }

    private SimpleUrlAuthenticationFailureHandler getAuthenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
        simpleUrlAuthenticationFailureHandler.setRedirectStrategy(getRedirectStrategy());
        simpleUrlAuthenticationFailureHandler.setDefaultFailureUrl(loginFailureUrl());
        return simpleUrlAuthenticationFailureHandler;
    }

    private AuthenticationSuccessHandler getSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl(properties.getLogin().getSuccessUrl());
        handler.setRedirectStrategy(getRedirectStrategy());
        return handler;
    }

    private RedirectStrategy getRedirectStrategy() {
        DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();
        defaultRedirectStrategy.setContextRelative(urlManager.isRootContext());
        return defaultRedirectStrategy;
    }

    private String loginUrl() {
        return "/" + getLoginPage();
    }

    public String loginFailureUrl() {
        return loginUrl() + "?error=1";
    }

    private String logoutUrl() {
        return loginUrl() + "?logout=1";
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean()
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) {
        WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();

        configurator.config(ignoring);

        ignoring.antMatchers(

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // (development mode) H2 debugging console
                "/h2-console/**",

                "/swagger-ui/index.html",

                "/error"
        );


        for (UaaHtmlPage htmlPage : UaaHtmlPage.values()) {
            ignoring.antMatchers(HttpMethod.GET, "/" + htmlPage.getHtmlFileName());
        }

        //Без этой строчки при /logout происходит редирект на /login?logout=1, к которому нет доступа
        //и далее идет редирект на /login.
        ignoring.antMatchers(HttpMethod.GET, "/" + getLoginPage());
    }

    private String getLoginPage() {
        return properties.getLogin().getPage();
    }


    @Bean
    public RequestRejectedHandler requestRejectedHandler() {
        // sends an error response with a configurable status code (default is 400 BAD_REQUEST)
        // we can pass a different value in the constructor
        return new HttpStatusRequestRejectedHandler();
    }
}
