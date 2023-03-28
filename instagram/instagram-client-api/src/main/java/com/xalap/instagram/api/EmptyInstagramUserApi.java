/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.api;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.api.user.User;

import java.util.*;

/**
 * Пустая реализация, которая не делает никуда никаких запросов
 *
 * @author Usov Andrey
 * @since 30.10.2021
 */
public class EmptyInstagramUserApi implements InstagramUserApi {

    @Override
    public Optional<User> readUser(InstagramAccount account, String userName) throws ServiceTemporaryException {
        return Optional.empty();
    }

    @Override
    public List<Media> getLastMediaList(InstagramAccount account, String userName) throws ServiceTemporaryException {
        return new ArrayList<>();
    }

    @Override
    public List<Media> getAllMediaList(InstagramAccount account, String userName) throws ServiceTemporaryException {
        return new ArrayList<>();
    }

    @Override
    public Set<String> getFollowers(InstagramAccount account, String userName) throws ServiceTemporaryException {
        return new HashSet<>();
    }

    @Override
    public Set<String> getLastFollowers(InstagramAccount account, String userName, int followersCount) throws ServiceTemporaryException {
        return new HashSet<>();
    }
}
