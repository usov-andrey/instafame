/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.framework.utils.MinMax;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOLikesTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import com.xalap.instagram.service.media.MediaBean;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Простановка лайков на следующие публикации
 *
 * @author Usov Andrey
 * @since 19.06.2021
 */
@AllArgsConstructor
public class NextLikesOrder {

    private final IOTaskProvider taskProvider;
    private final IOTaskService service;
    private final IOBean bean;
    private final CheatTaskBean likesTask;

    public void order() {
        //Определяем сколько новых публикаций было добавлено
        //Новая публикация - это публикация, время у которой больше чем время заказа
        List<MediaBean> mediaBeans = service.getNewMediaList(bean);
        if (mediaBeans.isEmpty()) {
            return;
        }
        //Нам нужно проставить лайки на новые публикации, на которые еще не проставляли до этого
        Set<String> mediaCodesWithLikes = getMediaCodesWithLikes(bean);
        //На сколько публикаций нам еще нужно проставить лайки
        int likesMediaCount = bean.getLastMediaCount() - mediaCodesWithLikes.size();
        //Если мы уже проставили нужное количество публикаций, то не нужно больше проставлять
        if (likesMediaCount == 0) {
            return;
        }
        //Сколько нужно проставить лайков на одну публикацию
        int likesForOneMedia = bean.getLikesCount() / bean.getLastMediaCount();
        orderNextLikes(mediaBeans, mediaCodesWithLikes, likesMediaCount, likesForOneMedia);
    }

    private void orderNextLikes(List<MediaBean> mediaBeans, Set<String> mediaCodesWithLikes,
                                int likesMediaCount, int likesForOneMedia) {
        //Список публикаций на которые нам нужно проставить лайки
        List<MediaBean> mediaForLikes = mediaBeans.stream()
                .filter(mediaBean -> !mediaCodesWithLikes.contains(mediaBean.getCode())).collect(Collectors.toList());
        int orderedMediaCount = 0;
        for (MediaBean mediaForLike : mediaForLikes) {
            if (orderedMediaCount == likesMediaCount) {
                break;
            }
            service.orderLikesWithViews(mediaForLike, bean, likesTask,
                    likesForOneMedia, new MinMax(0, 10),
                    null);
            orderedMediaCount++;
        }
    }

    /**
     * @return Коды публикаций для которых уже проставлены лайки
     */
    private Set<String> getMediaCodesWithLikes(IOBean bean) {
        List<IOTaskBean> likeTasks = taskProvider.getTasks(bean, InstaOrderTaskType.likes);
        return likeTasks.stream().map(ioTaskBean -> {
            IOLikesTaskBean likesTaskBean = (IOLikesTaskBean) ioTaskBean;
            return likesTaskBean.getCode();
        }).collect(Collectors.toSet());
    }
}
