/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.provider.robokassa;

import com.xalap.payment.provider.robokassa.properties.RobokassaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация
 *
 * @author Usov Andrey
 * @since 17.05.2021
 */
@Configuration
@EnableConfigurationProperties(RobokassaProperties.class)
public class RobokassaConfiguration {
}
