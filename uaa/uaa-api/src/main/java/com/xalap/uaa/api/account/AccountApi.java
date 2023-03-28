/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.account;

import com.xalap.uaa.api.user.UserDto;

/**
 * Интерфейс по работе с аккаунтом
 *
 * @author Usov Andrey
 * @since 14.05.2021
 */
public interface AccountApi {
    /**
     * @return Пользователя по логину
     */
    UserDto getUser(String login);

    /**
     * @return логин авторизованного пользователя
     */
    String getAuthenticatedUserLogin();

    /**
     * Создать пользователя с именем name и email и отправить ему письмо для подтверждения
     */
    void registerAccount(String name, String email);

    /**
     * Запрос письмо на смену пароля
     */
    void requestPasswordReset(String email);
}
