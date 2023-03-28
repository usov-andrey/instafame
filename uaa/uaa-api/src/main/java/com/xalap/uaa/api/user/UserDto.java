/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.user;

import com.xalap.framework.domain.holder.IdHolder;

import java.time.Instant;

/**
 * Пользователь
 *
 * @author Usov Andrey
 * @since 14.05.2021
 */
public interface UserDto extends IdHolder<Long> {

    String getLogin();

    // Lowercase the login before saving it in database
    void setLogin(String login);

    String getPassword();

    void setPassword(String password);

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    boolean getActivated();

    void setActivated(boolean activated);

    String getActivationKey();

    void setActivationKey(String activationKey);

    String getResetKey();

    void setResetKey(String resetKey);

    Instant getResetDate();

    void setResetDate(Instant resetDate);
}
