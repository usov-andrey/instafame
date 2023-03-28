/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.mail;

import com.xalap.framework.notification.NotificationService;
import com.xalap.uaa.api.account.UaaMailApi;
import com.xalap.uaa.api.user.UserDto;
import lombok.AllArgsConstructor;

/**
 * @author Usov Andrey
 * @since 03.04.2022
 */
@AllArgsConstructor
public class NotificationUaaMailService implements UaaMailApi {

    private final NotificationService notificationService;

    @Override
    public void sendActivationEmail(UserDto user, String password, String activateUrl) {
        notificationService.sendMessage(String.format("Отправляем письмо для активации пароля %s для пользователя %s",
                password, user));
    }

    @Override
    public void sendPasswordResetMail(UserDto user) {
        notificationService.sendMessage("Отправляем письмо для сброса пароля для " + user);
    }
}
