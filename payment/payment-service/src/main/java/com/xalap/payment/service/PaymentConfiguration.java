/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.service;

import com.xalap.uaa.api.config.SecurityConfigurator;
import org.springframework.context.annotation.Configuration;

/**
 * @author Usov Andrey
 * @since 2020-04-16
 */
@Configuration
public class PaymentConfiguration {

    public PaymentConfiguration(SecurityConfigurator configurator) {
        configurator.addAnonymousAccess(PaymentController.NAME);
    }

}
