/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.account;

import com.xalap.uaa.api.user.UserDto;

/**
 * Интерфейс по отправке писем связанных с аккаунтом
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
public interface UaaMailApi {

    /**
     * Отправить письмо для активации аккаунта
     * В письме будет также пароль
     */
    void sendActivationEmail(UserDto user, String password, String activateUrl);

    /**
     * Отправить письмо для сброса пароля
     */
    void sendPasswordResetMail(UserDto user);
}
