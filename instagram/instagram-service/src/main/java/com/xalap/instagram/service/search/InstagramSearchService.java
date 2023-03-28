/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.search;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instagram.api.user.User;
import com.xalap.instagram.service.api.InstagramClient;
import com.xalap.instagram.service.user.InstagramUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 18/01/2019
 */
@Service
@AllArgsConstructor
public class InstagramSearchService {

    private static final Logger log = LoggerFactory.getLogger(InstagramSearchService.class);
    private final InstagramClient instagramClient;
    private final InstagramUserService userService;

    /**
     * @return Список пользователей инстаграм
     */
    List<InstagramUserDTO> getUsers(String term, String clientId) {
        if (StringHelper.isEmpty(term) || term.length() < 3 || !instagramClient.isLoginCorrect(term)) {
            return new ArrayList<>();
        }
        InstagramUserDTO user = getUser(term, clientId);
        if (StringHelper.isEmpty(user.getValue())) {
            return new ArrayList<>();
        }
        return Collections.singletonList(user);
    }

    public InstagramUserDTO getUser(String term, String clientId) {
        if (StringHelper.isNotEmpty(term) && term.length() > 2) {
            String userName = InstagramClient.correctUserName(term);
            //Возможно заместо ссылки на аккаунт пользователь ввел ссылку на фотографию
            //https://www.instagram.com/p/Byr9Yt4IxCx/?igshid=djzaemh30ikr
            if (userName.equals("p")) {
                return new InstagramUserDTO("", "", "", false);
                /*
                String mediaCode = instagramClient.getMediaCode(term);
                if (StringHelper.isNotEmpty(mediaCode) && mediaCode.length() > 2) {
                    MediaWithLastActivity media = instagramAPIService.anonymousAPI().getMedia(mediaCode);
                    if (media != null) {
                        userName = media.getOwner().toLowerCase();
                    }
                }*/
            }
            if (userName.length() > 2) {
                try {
                    Optional<User> user = userService.readUser(userName);
                    if (user.isPresent()) {
                        log.info("Поиск instagram:" + term + " by ClientId:" + clientId);
                        return instagramUser(user.get());
                    }
                } catch (ServiceTemporaryException e) {
                    e.log(log, "Error on get user:" + userName);
                }
            }
        }
        return new InstagramUserDTO("", "", "", false);
    }


    private InstagramUserDTO instagramUser(User user) {
        return new InstagramUserDTO(user.getUserName(), user.getFullName(),
                user.getProfilePicture(), user.isPrivate());
    }
}
