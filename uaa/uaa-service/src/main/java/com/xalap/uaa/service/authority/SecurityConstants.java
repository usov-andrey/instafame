/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.authority;

/**
 * Разные константы
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
public final class SecurityConstants {

    //Такое значение зашито в AnonymousAuthenticationFilter в спринге
    public static final String ANONYMOUS_PRINCIPAL_NAME = "anonymousUser";

    public static final String SYSTEM_ACCOUNT = "system";

    private SecurityConstants() {
    }
}
