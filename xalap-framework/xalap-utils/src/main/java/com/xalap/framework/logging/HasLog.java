/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Используется для более быстрого в коде обращения к логгеру
 * 
 * @author Usov Andrey
 * @since 2020-04-28
 */
public interface HasLog {

    default Logger log() {
        return LoggerFactory.getLogger(getClass());
    }
}
