/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service;

import com.xalap.crm.service.integration.sendgrid.SendGridService;
import com.xalap.framework.notification.NotificationService;
import com.xalap.uaa.api.account.UaaMailApi;
import com.xalap.uaa.api.user.UserDto;

/**
 * @author Usov Andrey
 * @since 2020-04-14
 */
public class InstaFameUaaMailService implements UaaMailApi {

    private final NotificationService notificationService;
    private final SendGridService mailService;

    public InstaFameUaaMailService(NotificationService notificationService, SendGridService mailService) {
        this.notificationService = notificationService;
        this.mailService = mailService;
    }

    @Override
    public void sendActivationEmail(UserDto user, String password, String activateUrl) {
        notificationService.sendMessage("Зарегистрировался новый пользователь " + user + " пароль:" + password
                + " ссылка для активации:" + activateUrl);
        //Пока делаем невозможным регистрацию новых пользователей самостоятельно
        /*
        SendGridSendMailRequest mail = mailService.createMail("InstaFame", "noreply@instafame.ru",
                user.getEmail(), user.getName(),
                "d-e044e607ace145c4a8528f44709c66c2");
         //Параметры шаблона
         //   {
         //       "NAME" : "Андрей",
         //       "EMAIL" : "like.to.andrey@gmail.com",
         //       "PASSWORD" : "pass",
         //       "LINK": "https://instaboss.ru/activate?key=123"
         //   }

        mail.fill("NAME", user.getName());
        mail.fill("EMAIL", user.getEmail());
        mail.fill("PASSWORD", password);
        mail.fill("LINK", activateUrl);
        mailService.send(mail);*/
    }

    @Override
    public void sendPasswordResetMail(UserDto user) {
        notificationService.sendMessage("Отправляем письмо для сброса пароля для " + user);
    }
}
