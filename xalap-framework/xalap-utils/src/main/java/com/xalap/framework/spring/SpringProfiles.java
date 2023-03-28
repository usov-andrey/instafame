/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Профили спринга, к которым возможно обращение из кода
 * 
 * @author Usov Andrey
 * @since 20.11.2020
 */
@Service
public class SpringProfiles {

    public static final String DEV = "dev";

    @Autowired
    private Environment environment;

    public boolean isDevelopment() {
        return Arrays.asList(environment.getActiveProfiles()).contains(DEV);
    }
}
