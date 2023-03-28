/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.api;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.api.user.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Получение информации о пользователях из инстаграм
 *
 * @author Usov Andrey
 * @since 30.04.2021
 */
public interface InstagramUserApi {

    /**
     * Возвращает информацию о полььзователе
     */
    Optional<User> readUser(InstagramAccount account, String userName) throws ServiceTemporaryException;

    /**
     * Получаем информацию о последних 12 публикациях пользователя
     */
    List<Media> getLastMediaList(InstagramAccount account, String userName) throws ServiceTemporaryException;

    /**
     * Получаем информацию о всех публикациях пользователя
     */
    List<Media> getAllMediaList(InstagramAccount account, String userName) throws ServiceTemporaryException;

    /**
     * Возвращает всех подписчиков у userName
     */
    Set<String> getFollowers(InstagramAccount account, String userName) throws ServiceTemporaryException;

    /**
     * Возвращает подписчиков у userName
     */
    Set<String> getLastFollowers(InstagramAccount account, String userName, int followersCount) throws ServiceTemporaryException;

}
