/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser;

import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOFollowersPackage;
import com.xalap.instafame.service.instaorder.settings.InstaOrderSettingsService;
import org.springframework.stereotype.Service;

/**
 * Старая реализация парсера строки заказа
 *
 * @author Usov Andrey
 * @since 2020-07-18
 */
@Service
public class OldOrderLineParser {

    private final InstaOrderSettingsService settingsService;

    public OldOrderLineParser(InstaOrderSettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public void parse(IOBean bean, String value) {
        bean.setFollowersPackage(IOFollowersPackage.none);
        String followers = "подписчиков";
        String likes = "лайков";
        String comments = "комм.";
        //(Имя пакета + N + followers) + (M + likes) + (K + comments) - каких-то блоков может не быть
        if (value.contains(followers)) {
            //Получаем информацию о подписчиках
            IOFollowersPackage followersPackage = IOFollowersPackage.getValue(value);
            String followersCount = StringHelper.getStringBetweenNotEmpty(value, followersPackage.getCaption(), followers).trim();
            bean.setFollowersPackage(followersPackage);
            bean.setFollowersCount(Integer.parseInt(followersCount));
            value = StringHelper.getStringAfter(value, followers);
        }
        if (value.contains(likes)) {
            String likesCount = StringHelper.getStringBefore(value, likes).trim();
            bean.setLikesCount(Integer.parseInt(likesCount));
            value = StringHelper.getStringAfter(value, likes);
        }
        if (value.contains(comments)) {
            String commentsCount = StringHelper.getStringBefore(value, comments).trim();
            bean.setCommentsCount(Integer.parseInt(commentsCount));
        }
        if (bean.getFollowersPackage() == IOFollowersPackage.vip) {
            bean.setLikesCount(bean.getLikesCount() + settingsService.getVipLikesCount());
            bean.setCommentsCount(bean.getCommentsCount() + settingsService.getVipCommentsCount());
        }
    }
}
