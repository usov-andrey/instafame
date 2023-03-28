/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.configuration;

import com.xalap.instagram.mgp25.MacOsPhpExecutor;
import com.xalap.instagram.mgp25.PhpExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Configuration
@Profile("macos")
public class MacOsConfiguration {

    @Bean
    public PhpExecutor phpExecutor() {
        return new MacOsPhpExecutor();
    }

}
