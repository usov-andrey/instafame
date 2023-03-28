/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.mail;

import com.xalap.uaa.api.account.UaaMailApi;
import com.xalap.uaa.api.user.UserDto;
import org.springframework.stereotype.Service;

/**
 * Ничего не делаем
 *
 * @author Usov Andrey
 * @since 03.04.2022
 */
@Service
public class EmptyUaaMailService implements UaaMailApi {

    @Override
    public void sendActivationEmail(UserDto user, String password, String activateUrl) {

    }

    @Override
    public void sendPasswordResetMail(UserDto user) {

    }
}
