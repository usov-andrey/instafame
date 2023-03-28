/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.MinMax;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import com.xalap.instagram.service.media.MediaBean;
import com.xalap.instagram.service.media.MediaService;
import com.xalap.instagram.service.user.InstagramUserService;

import java.util.*;

/**
 * @author Усов Андрей
 * @since 24/04/2019
 */
public class VipLikeRunner extends CheatAutoRunner {

    private final InstagramUserService userService;
    private final MediaService mediaService;

    public VipLikeRunner(IOTaskService service, IOTaskProvider taskProvider, InstagramUserService userService, MediaService mediaService) {
        super(service, taskProvider);
        this.userService = userService;
        this.mediaService = mediaService;
    }

    public void run(IOBean bean) {
        if (bean.getUser() == null) {
            return;
        }
        Optional<CheatTaskBean> likesTask = service.getSettings().getLikesTask();
        if (likesTask.isPresent()) {
            IOStats.Stat likeStats = new IOStats(bean, taskProvider.getTasks(bean)).getLikes();
            //Если нужно еще проставлять лайки и план по лайкам пустой, по составляем план
            if (likeStats.getPlaned() < likeStats.getMax() && likeStats.getPlaned() == likeStats.getCurrent()) {
                if (bean.getFollowersCount() == 0) {
                    throw new IllegalStateException("Not implemented");
                }
                IOStats.Stat progressHours = bean.progressHours();
                //Максимально сколько нам нужно раздать лайков до следующего периода
                int hoursAfterPeriod = progressHours.getCurrent() + PERIOD_HOURS;
                if (hoursAfterPeriod > progressHours.getMax()) {
                    hoursAfterPeriod = progressHours.getMax();
                }
                int likesCount = likeStats.getMaxMinusCurrent() * hoursAfterPeriod / progressHours.getMax();
                if (likesCount > 0) {
                    List<MediaBean> userLastMedia = mediaService.getUserLastMediaList(bean.getUser(), 12);
                    if (!userLastMedia.isEmpty()) {
                        Map<MediaBean, Integer> likesMap = calculateLikes(userLastMedia, likesCount, likesTask.get());
                        for (MediaBean mediaBean : likesMap.keySet()) {
                            //Заказываем лайки
                            int likesForMedia = likesMap.get(mediaBean);
                            orderLikesByHour(likesForMedia, progressHours, mediaBean, bean, likesTask.get());
                        }
                    }
                }
            }
        }
    }

    private void orderLikesByHour(int likesCount, IOStats.Stat progressHours, MediaBean mediaBean,
                                  IOBean bean, CheatTaskBean likesTask) {
        int divCount = divCount(progressHours);
        int minForOneMedia = likesTask.getMin();
        int likesForOneTime = likesCount / divCount;
        if (likesForOneTime < minForOneMedia) {
            likesForOneTime = minForOneMedia;
        }
        Date time = new Date();
        while (likesCount > 0) {
            //Последнюю порцию добавляем позже
            if (likesCount - likesForOneTime < minForOneMedia) {
                likesForOneTime = likesCount;
            }
            //Проставляем лайки 1 в минуту
            service.orderLikes(mediaBean.getCode(), mediaBean, bean, likesTask, likesForOneTime, new MinMax(0, 5), 1, time);
            time = DateHelper.incHours(time, 1);
            likesCount = likesCount - likesForOneTime;
        }
    }

    /**
     * @return на сколько частей разбивать лайки
     */
    private int divCount(IOStats.Stat progressHours) {
        //Пока что не разбиваем на части
        return 1;
        /*
        //Нужно разбить это количество лайков, чтобы они прибывали равномерно по часам
        int hours = progressHours.getMax() - progressHours.getCurrent();
        if (hours > PERIOD_HOURS) {
            hours = PERIOD_HOURS;
        }
        int count = hours - 1;
        if (count < 1) {
            count = 1;
        }
        return count;*/
    }

    private Map<MediaBean, Integer> calculateLikes(List<MediaBean> userLastMedia, int likesCount, CheatTaskBean likesTask) {
        Map<MediaBean, Integer> result = new HashMap<>();
        int maxLikesForOneMedia = medianOfLikes(userLastMedia).getStats().getLikesCount();
        int minForOneMedia = likesTask.getMin();
        MediaBean.sortByNormalizedLikes(userLastMedia);
        for (MediaBean mediaBean : userLastMedia) {
            int likesForMedia = maxLikesForOneMedia - mediaBean.getStats().getLikesCount();
            if (likesForMedia > 0) {
                if (likesForMedia > likesCount) {
                    likesForMedia = likesCount;
                }
                if (likesForMedia < minForOneMedia) {
                    likesForMedia = minForOneMedia;
                }
            }
            result.put(mediaBean, likesForMedia + (result.containsKey(mediaBean) ? result.get(mediaBean) : 0));
            likesCount = likesCount - likesForMedia;
            if (likesCount <= 0) {
                break;
            }
        }
        //Если еще остались не распределенные лайки, то распределяем их равномерно по всем публикациям
        Iterator<MediaBean> mediaBeanIterator = CollectionHelper.repeatIterator(userLastMedia);
        while (likesCount > 0) {
            MediaBean mediaBean = mediaBeanIterator.next();
            result.put(mediaBean, minForOneMedia + (result.containsKey(mediaBean) ? result.get(mediaBean) : 0));
            likesCount = likesCount - minForOneMedia;
        }
        return result;
    }

    private MediaBean medianOfLikes(List<MediaBean> mediaList) {
        Collections.sort(mediaList, (o1, o2) -> Integer.compare(o1.getStats().getLikesCount(), o2.getStats().getLikesCount()));
        return mediaList.get(mediaList.size() / 2);
    }


}
