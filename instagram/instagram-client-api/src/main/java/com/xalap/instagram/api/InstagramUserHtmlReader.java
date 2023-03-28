/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.api;

import com.xalap.instagram.api.user.User;

/**
 *
 * @author Usov Andrey
 * @since 30.04.2021
 */
public interface InstagramUserHtmlReader {

    /**
     * Прочитать информацию о полььзователе из html
     */
    User readUserFromHtml(String html);
}
