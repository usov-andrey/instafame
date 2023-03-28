/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.provider.robokassa.properties;

import org.springframework.stereotype.Component;

/**
 * Определяет откуда брать настройки robokassa
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Component
public class RobokassaPropertiesProvider {

    private final RobokassaProperties robokassaProperties;

    public RobokassaPropertiesProvider(RobokassaProperties robokassaProperties) {
        this.robokassaProperties = robokassaProperties;
    }

    public RobokassaProperties getRobokassaProperties() {
        return robokassaProperties;
    }
}
