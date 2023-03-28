/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.api;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.logging.HasLog;
import com.xalap.framework.task.async.AsyncRunService;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instagram.api.InstagramAccount;
import com.xalap.instagram.api.InstagramUserApi;
import com.xalap.instagram.api.InstagramUserHtmlReader;
import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.api.user.User;
import com.xalap.instagram.service.account.InstagramAccountProvider;
import com.xalap.instagram.service.media.MediaListener;
import com.xalap.instagram.service.media.MediaService;
import com.xalap.instagram.service.user.InstagramUserBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Клиент для инстаграм, в зависимости от настроек происходит работа или через mgp или http или selenium
 *
 * @author Usov Andrey
 * @since 2020-05-22
 */
@Service
@AllArgsConstructor
public class InstagramClient implements HasLog {

    private static final String instagramLoginSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_.";

    private final InstagramUserApi userApi;
    private final InstagramUserHtmlReader userHtmlReader;
    private final MediaService mediaService;
    private final InstagramAccountProvider accountProvider;
    private final AsyncRunService asyncRunService;

    /**
     * Обновляем информацию о последних 12 публикациях
     */
    public void updateLastMediaList(User user, InstagramUserBean userBean, MediaListener mediaListener) {
        //Если мы делали запрос через mgp, то информации о последних медиа у нас нет
        if (user.getLastMedias() == null) {
            //Получение media может затянуться, поэтому делаем это в отдельном потоке/транзакции
            asyncRunService.run(() -> {
                updateMediaList(getLastMediaList(userBean), userBean, mediaListener);
            });
        } else {
            //Значит мы делали получение через обычное http и у нас есть информация о последних media
            updateMediaList(user.getLastMedias(), userBean, mediaListener);
        }
    }

    private void updateMediaList(List<Media> mediaList, InstagramUserBean userBean, MediaListener mediaListener) {
        if (mediaList.isEmpty()) {
            return;
        }
        for (Media media : mediaList) {
            mediaService.updateMedia(media, userBean.getUserName(), mediaListener);
        }
        //Нужно удалить удаленные media
        deleteMedias(mediaList, userBean);
        mediaListener.mediaUpdated();
    }

    /**
     * Обработать удаленные пользователем публикации
     */
    private void deleteMedias(List<Media> mediaList, InstagramUserBean userBean) {
        Set<String> mediaIdSet = mediaList.stream().map(Media::getId).collect(Collectors.toSet());
        Optional<Date> maxCreateTimeOptional = getDateStream(mediaList).max(Date::compareTo);
        Optional<Date> minCreateTimeOptional = getDateStream(mediaList).min(Date::compareTo);
        log().debug("Delete medias in user:{} by mediaIdSet:{} and minCreateTime:{} and maxCreateTime:{}",
                userBean, mediaIdSet, minCreateTimeOptional, maxCreateTimeOptional);
        maxCreateTimeOptional.ifPresent(maxCreateTime -> minCreateTimeOptional.ifPresent(minCreateTime ->
                mediaService.deleteMedias(userBean, mediaIdSet, minCreateTime, maxCreateTime)));
    }

    private Stream<Date> getDateStream(List<Media> mediaList) {
        return mediaList.stream().map(Media::getCreateTime);
    }

    private List<Media> getLastMediaList(InstagramUserBean userBean) {
        try {
            return userApi.getLastMediaList(account(), userBean.getUserName());
        } catch (ServiceTemporaryException e) {
            log().error("Error on get last media list for user:" + userBean.getUserName(), e);
            return new ArrayList<>();
        }
    }

    private InstagramAccount account() {
        return accountProvider.getForReadOperations();
    }


    public Set<String> getFollowers(String userName) throws ServiceTemporaryException {
        return userApi.getFollowers(account(), userName);
    }


    /**
     * @param followersCount - сколько последних подписчиков считывать
     * @return Возвращает последних подписчиков у userName
     */
    public Set<String> getLastFollowers(String userName, int followersCount) throws ServiceTemporaryException {
        return userApi.getLastFollowers(account(), userName, followersCount);
    }

    /**
     * Обновляет информацию о последних count публикаций у пользователя userName
     * и ждет окончания обновления
     */
    public void updateMediaList(InstagramUserBean userBean, MediaListener mediaListener) throws ServiceTemporaryException {
        updateMediaList(userApi.getAllMediaList(account(), userBean.getUserName()), userBean, mediaListener);
    }

    public Optional<User> getUser(String userName) throws ServiceTemporaryException {
        return userApi.readUser(account(), userName);
    }

    public User readUserFromHtml(String html) {
        return userHtmlReader.readUserFromHtml(html);
    }

    public boolean isLoginCorrect(String login) {
        if (StringHelper.isEmpty(login) || "admin".equals(login)) {
            return false;
        }
        for (char c : login.toCharArray()) {
            if (!instagramLoginSymbols.contains(c + "")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Из ссылки вида, возвращает код медиа
     * https://www.instagram.com/p/Byr9Yt4IxCx/?igshid=djzaemh30ikr
     */
    public static String getMediaCode(String url) {
        return correctValue(url, "instagram.com/p/");
    }

    public static String correctUserName(String url) {
        return correctValue(url.toLowerCase(), "instagram.com/");
    }

    private static String correctValue(String url, String start) {
        String value = url.trim();
        //https://instagram.com/ms_jandarmova?igshid=3aav2h0mlxr3
        if (value.contains(start)) {
            value = StringHelper.getStringAfter(value, start);
        }
        //Случай https://www.instagram.com/monsieurmisha/
        //https://www.instagram.com/p/Byr9Yt4IxCx/?igshid=djzaemh30ikr
        if (value.contains("/")) {
            //Возможно заместо ссылки на аккаунт пользователь ввел ссылку на фотографию
            //https://www.instagram.com/p/Byr9Yt4IxCx/?igshid=djzaemh30ikr
            //тогда этот метод вернет имя пользователя равное p
            value = StringHelper.getStringBefore(value, "/");
        }
        //Случай https://instagram.com/nikitakrv?utm_source=ig_profile_share&...
        //Случай https://www.instagram.com/p/Byr9Yt4IxCx?utm_source=ig_profile_share&...
        if (value.contains("?")) {
            value = StringHelper.getStringBefore(value, "?");
        }
        value = StringHelper.replace(value, "@", "");
        return value;
    }

    public static String accountURL(String instagram) {
        return "https://www.instagram.com/" + instagram;
    }

    public static String mediaUrl(String code) {
        return "https://www.instagram.com/p/" + code;
    }
}
