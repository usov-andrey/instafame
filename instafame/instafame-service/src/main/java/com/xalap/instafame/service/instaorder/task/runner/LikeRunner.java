/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.utils.MinMax;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOMediaDirection;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import com.xalap.instagram.service.user.InstagramUserBean;

import java.util.Date;
import java.util.Optional;

/**
 * После заказа перед простановкой лайков нужно убедиться, что
 * время обновления информации и media позже чем время заказа
 *
 * @author Usov Andrey
 * @since 24.05.2021
 */
public class LikeRunner extends CheatAutoRunner {

    private final NotificationService notificationService;

    public LikeRunner(IOTaskService service, IOTaskProvider taskProvider, NotificationService notificationService) {
        super(service, taskProvider);
        this.notificationService = notificationService;
    }

    @Override
    public void run(IOBean bean) {
        int likesCount = bean.getLikesCount();
        Integer lastMediaCount = bean.getLastMediaCount();
        InstagramUserBean user = bean.getUser();
        if (likesCount == 0 || lastMediaCount == null || lastMediaCount == 0 || user == null) {
            log.debug("Skip likeRunner for io:{}", bean);
            return;
        }
        Optional<CheatTaskBean> likesTaskOptional = service.getSettings().getLikesTask();
        likesTaskOptional.ifPresent(likesTask -> {
            //Запускаем простановку лайков только если после заказа мы хотя бы раз обновили информацию о публикциях
            Date mediaListUpdateTime = user.getMediaListUpdateTime();
            if (mediaListUpdateTime != null && mediaListUpdateTime.after(bean.getCreateTime())) {
                log.debug("Order likes for io:{}", bean);
                if (IOMediaDirection.NEXT.equals(bean.getMediaDirection())) {
                    new NextLikesOrder(taskProvider, service, bean, likesTask).order();
                } else {
                    orderLastLikes(bean, lastMediaCount, user, likesTask);
                }
            }
        });
    }

    /**
     * Делаем заказ на уже выпущенные публикации
     */
    private void orderLastLikes(IOBean bean, Integer lastMediaCount, InstagramUserBean user, CheatTaskBean likesTask) {
        IOStats ioStats = new IOStats(bean, taskProvider.getTasks(bean));
        IOStats.Stat likeStats = ioStats.getLikes();
        IOStats.Stat views = ioStats.getViews();
        //Распределяем только один раз
        if (likeStats.getPlaned() == 0) {
            int newLikesCount = likeStats.getMaxMinusCurrent();
            if (newLikesCount > 0) {
                orderLastLikes(bean, lastMediaCount, user, likesTask, newLikesCount, views);
            }
        } else if (likeStats.getPlaned() + likeStats.getCurrent() < likeStats.getMax()) {
            notificationService.sendMessage("Нужно ручное распределение лайков для пользователя " +
                    user.getUserName());
        }
    }

    private void orderLastLikes(IOBean bean, Integer lastMediaCount, InstagramUserBean user, CheatTaskBean likesTask,
                                int newLikesCount, IOStats.Stat views) {
        int likesForMedia = newLikesCount / lastMediaCount;
        if (likesForMedia > likesTask.getMin()) {
            service.orderForLastMedias(bean, lastMediaCount,
                    mediaBean -> {
                        if (views.started()) {
                            service.orderLikes(mediaBean, bean, likesTask, likesForMedia, new MinMax(0, 10), null);
                        } else {
                            service.orderLikesWithViews(mediaBean, bean, likesTask,
                                    likesForMedia, new MinMax(0, 10),
                                    null);
                        }
                    });
        } else {
            notificationService.sendMessage(String.format("Пользователь %s сделал заказ меньше " +
                            "чем минимальное количество лайков на публикацию %d", user.getUserName(),
                    likesTask.getMin()));
        }
    }
}
