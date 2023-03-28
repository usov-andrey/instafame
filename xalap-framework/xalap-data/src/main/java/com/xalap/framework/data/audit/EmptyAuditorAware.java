/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author Usov Andrey
 * @since 22.03.2022
 */
public class EmptyAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}
