/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.exception.SupplierWithServiceException;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instagram.api.user.User;
import com.xalap.instagram.service.api.InstagramClient;
import com.xalap.instagram.service.media.MediaBean;
import com.xalap.instagram.service.media.MediaListener;
import com.xalap.instagram.service.media.MediaService;
import com.xalap.instagram.service.user.follower.UserFollowerBean;
import com.xalap.instagram.service.user.follower.UserFollowerService;
import com.xalap.instagram.service.user.history.UserHistoryBean;
import com.xalap.instagram.service.user.history.UserHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Service
public class InstagramUserService extends CrudService<InstagramUserBean, InstagramUserRepository, Integer> {

    private static final Logger log = LoggerFactory.getLogger(InstagramUserService.class);

    private final InstagramUserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final UserFollowerService userFollowerService;
    private final MediaService mediaService;
    private final InstagramClient instagramClient;

    public InstagramUserService(InstagramUserRepository userRepository, UserHistoryRepository userHistoryRepository, UserFollowerService userFollowerService, MediaService mediaService, InstagramClient instagramClient) {
        super();
        this.userRepository = userRepository;
        this.userHistoryRepository = userHistoryRepository;
        this.userFollowerService = userFollowerService;
        this.mediaService = mediaService;
        this.instagramClient = instagramClient;
    }

    /**
     * Получаем информацию о пользователе или создаем. При создании информация о последних публикациях также создается
     * Возвращаем empty, если пользователь не найден или если ошибка связанная с инстаграм
     */
    public Optional<InstagramUserBean> getOrCreateUser(String userName) {
        InstagramUserBean userBean = findUser(userName);
        if (userBean == null) {
            try {
                return updateUserWithLastMedia(userName);
            } catch (ServiceTemporaryException e) {
                log.error("Error on update user:" + userName, e);
                return Optional.empty();
            }
        }
        return Optional.of(userBean);
    }

    /**
     * Обновляет информацию о пользователе и его последних публикациях, но не чаще чем раз в minutes минут
     * В случае, если произошла ошибка при обновлении, то возвращается исходная информация о пользователе
     */
    public Optional<InstagramUserBean> updateUserWithLastMediaEveryMinutes(InstagramUserBean userBean, int minutes) {
        if (DateHelper.minutesBetweenDates(userBean.getUpdateTime(), new Date()) > minutes) {
            try {
                return updateUserWithLastMedia(userBean.getUserName());
            } catch (ServiceTemporaryException e) {
                e.log(log, "Error on update User:" + userBean.getUserName());
                //Считаем, что информация о пользователе не изменилась
                return Optional.of(userBean);
            }
        }
        return Optional.of(userBean);
    }

    /**
     * Обновляет информацию о последних count публикациях пользователя
     */
    public void updateUserWithAllMedia(String userName) throws ServiceTemporaryException {
        Optional<InstagramUserBean> userBeanOptional = getOrCreateUser(userName);
        if (userBeanOptional.isPresent()) {
            InstagramUserBean userBean = userBeanOptional.get();
            instagramClient.updateMediaList(userBean, createMediaListener(userBean));
        }
    }

    /**
     * Обновление информации о профиле, смотрим последние 12 постов
     * Если появился новый пост, вызываем newMediaConsumer
     * Возвращает null, если проблема с получением информации о пользователе
     */
    public Optional<InstagramUserBean> updateUserWithLastMedia(String userName) throws ServiceTemporaryException {
        Optional<User> userOptional = readUser(userName);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        User user = userOptional.get();
        InstagramUserBean userBean = updateUser(user);
        if (!userBean.isPrivate()) {
            if (user.getMediaCount() > 0) {
                instagramClient.updateLastMediaList(user, userBean, createMediaListener(userBean));
            }
        }
        return Optional.of(userBean);
    }

    private MediaListener createMediaListener(InstagramUserBean userBean) {
        return new MediaListener(userBean, mediaBean -> {
            if (userBean.getLastMediaCreateTime() == null || userBean.getLastMediaCreateTime().before(mediaBean.getCreateTime())) {
                userBean.setLastMediaCreateTime(mediaBean.getCreateTime());
                save(userBean);
            }
        }, () -> {
            userBean.setMediaListUpdateTime(new Date());
            save(userBean);
        });
    }

    @Async
    public void asyncUpdateUserWithLastMedia(String userName) {
        try {
            updateUserWithLastMedia(userName);
        } catch (ServiceTemporaryException e2) {
            e2.log(log, "Error on update media for user: " + userName);
        }
    }

    /**
     * Обновляем информацию только о пользователе, не о media
     */
    private Optional<InstagramUserBean> updateUser(String userName) throws ServiceTemporaryException {
        Optional<User> user = readUser(userName);
        return user.map(this::updateUser);
    }

    /**
     * Получаем информацию о пользователя из инстаграм
     * Если ошибка с инстаграм, то будет выкинуто исключение ServiceTemporaryException
     */
    public Optional<User> readUser(String userName) throws ServiceTemporaryException {
        //Если используют недопустимые в логине символы, то точно нет такого пользователя
        if (userName.contains("-")) {
            return Optional.empty();
        }
        return instagramClient.getUser(userName);
    }

    public InstagramUserBean readAndUpdateUserFromHtml(String html) {
        User user = instagramClient.readUserFromHtml(html);
        return updateUser(user);
    }

    /**
     * Ищем пользователя в базе
     */
    public InstagramUserBean findUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * Считываем информацию о пользователе и сохраняем изменения в базу
     */
    private InstagramUserBean updateUser(User user) {
        InstagramUserBean userBean = userRepository.findByInternalId(user.getId());
        if (userBean == null) {
            userBean = new InstagramUserBean();
            userBean.setCreateTime(new Date());
        } else {
            if (userBean.getUserStats().isChanged(user)) {
                //Добавляем информацию о предыдущих показателях пользователя
                UserHistoryBean userHistoryBean = new UserHistoryBean();
                userHistoryBean.setCreateTime(userBean.getUpdateTime());
                userHistoryBean.setUser(userBean);
                userHistoryBean.update(user);
                userHistoryRepository.save(userHistoryBean);
            }
        }
        userBean.update(user);
        return save(userBean);
    }



    /**
     * Прочитать информацию о последних followersCount подписчиках
     */
    public void readLastUserFollowers(String userName, int followersCount) {
        Optional<InstagramUserBean> userBeanOptional = SupplierWithServiceException.run(() -> updateUser(userName));
        if (userBeanOptional.isEmpty()) {
            return;
        }
        InstagramUserBean userBean = userBeanOptional.get();

        Set<String> followers = SupplierWithServiceException.run(() -> instagramClient.getLastFollowers(userName, followersCount));
        Map<String, UserFollowerBean> followersHistory = new HashMap<>();
        for (UserFollowerBean userFollowerBean : userFollowerService.repository().findByUser(userBean)) {
            followersHistory.put(userFollowerBean.getFollowerUserName(), userFollowerBean);
        }
        for (String follower : followers) {
            UserFollowerBean userFollowerBean = followersHistory.get(follower);
            if (userFollowerBean == null) {
                userFollowerService.createAndSave(userBean, follower);
            }
        }
    }

    /**
     * Читаем текущих подписчиков и изменяем историю подписок
     */
    public void updateUserFollowersHistory(String userName) {
        Optional<InstagramUserBean> userBeanOptional = SupplierWithServiceException.run(() -> updateUser(userName));
        if (userBeanOptional.isEmpty()) {
            return;
        }
        InstagramUserBean userBean = userBeanOptional.get();
        if (userBean.getUserStats().getFollowsCount() > 15000) {
            return;
        }

        Set<String> followers = SupplierWithServiceException.run(() -> instagramClient.getFollowers(userName));
        int followersCount = followers.size();
        //Если мы прочитали меньше 75 процентов, то пропускаем
        if ((followersCount * 100 / userBean.getUserStats().getFollowedByCount()) < 75) {
            log.warn("updateUserFollowersHistory for user:" + userName + " read " + followersCount + ", but real followers count:"
                    + userBean.getUserStats().getFollowedByCount());
            return;
        }
        int notReadFollowersCount = userBean.getUserStats().getFollowedByCount() - followersCount;
        Map<String, UserFollowerBean> followersHistory = new HashMap<>();
        Set<UserFollowerBean> toUnFollowSet = new HashSet<>();//Кого нужно отчислить
        for (UserFollowerBean userFollowerBean : userFollowerService.repository().findByUser(userBean)) {
            followersHistory.put(userFollowerBean.getFollowerUserName(), userFollowerBean);
            if (userFollowerBean.getUnFollowDate() == null) {
                toUnFollowSet.add(userFollowerBean);
            }
        }
        for (String follower : followers) {
            UserFollowerBean userFollowerBean = followersHistory.get(follower);
            if (userFollowerBean == null) {
                //Новый пользователь
                userFollowerService.createAndSave(userBean, follower);
            } else {
                if (userFollowerBean.getUnFollowDate() != null) {
                    //До этого пользователь отписался, а сейчас подписался опять
                    //Из-за того, что инстаграм иногда выдает не всех подписчиков, то возможна ситуация
                    //Что этот пользователь и не отписывался, а просто мы его не получили
                    //Считаем, что он подписался в самый первый раз и не отписывался
                    userFollowerBean.setUnFollowDate(null);
                    userFollowerBean.setNotSeenFollowFirstTime(null);//Очищаем дату, что его не видели в подписчиках
                    userFollowerService.save(userFollowerBean);
                } else {
                    //Убираем пользователя из того, кого нужно отчислять
                    toUnFollowSet.remove(userFollowerBean);
                    if (userFollowerBean.getNotSeenFollowFirstTime() != null) {
                        userFollowerBean.setNotSeenFollowFirstTime(null);//Очищаем дату, что его не видели в подписчиках
                        userFollowerService.save(userFollowerBean);
                    }
                }
            }
        }
        //Вычисляем кого нужно отписывать
        Date now = new Date();
        List<UserFollowerBean> toUnFollowList = new ArrayList<>();
        for (UserFollowerBean bean : toUnFollowSet) {
            //Если это первый раз, когда пользователя не увидели в подписчиках, то отчислять его не будем
            if (bean.getNotSeenFollowFirstTime() == null) {
                bean.setNotSeenFollowFirstTime(new Date());
                userFollowerService.save(bean);
            } else {
                //Отписывать нужно только тех, которых мы уже видели в отписанных 24 часа назад
                if (Math.abs(DateHelper.minutesBetweenDates(now, bean.getNotSeenFollowFirstTime())) > 60 * 24) {
                    toUnFollowList.add(bean);
                }
            }
        }


        //Вначале будем отчислять тех, которых не видели в подписчиках раньше всего
        toUnFollowList.sort(Comparator.comparing(UserFollowerBean::getNotSeenFollowFirstTime));
        //Из-за того, что мы получаем неточное количество подписчиков, то отчислять мы будем только на разницу, которую точно прочитали
        int unFollowCount = toUnFollowSet.size() - notReadFollowersCount;

        for (UserFollowerBean bean : toUnFollowList) {
            unFollowCount--;
            if (unFollowCount <= 0) {
                break;
            }
            //Отписываем оставшихся временем, когда они первый раз появились в неподписанных
            bean.setUnFollowDate(bean.getNotSeenFollowFirstTime());
            userFollowerService.save(bean);
        }
    }


    /**
     * Хэштеги используемые пользователем
     */
    public Set<String> hashTagNames(InstagramUserBean userBean) {
        List<MediaBean> userMediaList = mediaService.getUserMediaList(userBean);
        return mediaService.hashTagNameSet(userMediaList);
    }
}
