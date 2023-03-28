/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.IOTaskSpeed;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;

import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 07/10/2019
 */
public abstract class FollowersPackageRunner extends CheatAutoRunner {

    public FollowersPackageRunner(IOTaskService service, IOTaskProvider taskProvider) {
        super(service, taskProvider);
    }

    @Override
    public void run(IOBean bean) {
        //Если профиль приватный, то отправляем всех подписчиков эконом(они типо доходят до приватного профиля)
        if (bean.getUser() != null && bean.getUser().isPrivate()) {
            log.debug("No tasks for private user:" + bean.getUser());
            //Ничего не создаем
            return;
        }

        List<IOTaskBean> tasks = taskProvider.getTasks(bean, InstaOrderTaskType.followers);

        IOStats.Stat stat = new IOStats(bean, tasks).getFollowers();

        if (stat.getPlaned() < stat.getMax()) {
            int count = stat.getMax() - stat.getPlaned();
            if (tasks.isEmpty()) {
                orderFollowersForNextPeriod(bean, count);
            } else {
                //Если задачи уже есть, то мы или находимся в конце
                if (count > 0) {
                    //Или находимся в середине и нужно просто распланировать обычных подписчиков на следующие 24 часа
                    //Если какое-то задание по получению подписчиков еще выполняется, то ждем окончания выполнения
                    if (stat.getPlaned() == stat.getCurrent()) {
                        //Возможно мы сильно опережаем время и еще не пришло раздавать подписчиков
                        if (bean.progressMoreStat(stat)) {
                            //Планируем новое
                            orderFollowersForNextPeriod(bean, count);
                        }
                    }
                }
            }
        } else {
            //Значит нужно восстановление, правда если только нету текущей уже запланированной или в прогрессе задачи по набору подписчиков
            if (stat.getPlaned() == stat.getCurrent()) {
                restore(bean);
            }
        }
    }

    /**
     * Планируем выдачу подписчиков
     */
    private void orderFollowersForNextPeriod(IOBean bean, int count) {
        Date date = new Date();
        int followersPerDay = followersForNextPeriod(bean, count);
        orderFollowersForNextPeriod(bean, date, followersPerDay);
    }

    abstract protected void orderFollowersForNextPeriod(IOBean bean, Date date, int followersPerDay);

    protected void orderFollowers(IOBean bean, Date date, int followersPerDay, CheatTaskBean cheatTask) {
        IOTaskSpeed speed = IOTaskSpeed.followersPerDay(followersPerDay);
        if (service.getSettings().isMaximumFollowersSpeed()) {
            speed = IOTaskSpeed.s200;
        }
        if (followersPerDay < cheatTask.getMin()) {
            followersPerDay = cheatTask.getMin();
        }
        service.orderFollowers(bean, cheatTask, followersPerDay, speed.getSpeed(), date);
    }

    private int followersForNextPeriod(IOBean bean, int count) {
        //Расчитываем скорость, с которой нужно отдавать подписчиков
        int moreHours = bean.getOrderStrategy().progressHours(bean).getMaxMinusCurrent();
        if (moreHours < 0) {
            moreHours = 0;
        }
        int moreDays = moreHours / PERIOD_HOURS + 1;
        //Сколько нам нужно проставить подписчиков за следующие сутки
        return count / moreDays;
    }

}
