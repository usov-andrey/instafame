/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.config;

import com.xalap.uaa.service.config.login.LoginProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Настройки безопасности
 *
 * @author Usov Andrey
 * @since 04.04.2022
 */
@ConfigurationProperties(prefix = "xalap.uaa", ignoreUnknownFields = false)
public class SecurityProperties {

    @NestedConfigurationProperty
    private LoginProperties login = new LoginProperties();

    public LoginProperties getLogin() {
        return login;
    }

    public void setLogin(LoginProperties login) {
        this.login = login;
    }
}
