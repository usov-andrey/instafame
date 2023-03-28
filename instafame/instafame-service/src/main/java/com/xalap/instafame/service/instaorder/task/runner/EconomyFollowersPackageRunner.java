/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
public class EconomyFollowersPackageRunner extends FollowersPackageRunner {

    public EconomyFollowersPackageRunner(IOTaskService service, IOTaskProvider taskProvider) {
        super(service, taskProvider);
    }

    @Override
    protected void orderFollowersForNextPeriod(IOBean bean, Date date, int followersPerDay) {
        //Выбираем задачу для которой отправлять подписчиков
        Optional<CheatTaskBean> cheatTaskOptional = service.getSettings().getEconomyCheatTask();
        if (!cheatTaskOptional.isPresent()) {
            List<CheatTaskBean> tasks = service.getCheatTaskService().getEnabledSortedTasks(InstaOrderTaskType.followers);
            if (!tasks.isEmpty()) {
                //Возвращаем самых дешевых подписчиков
                cheatTaskOptional = Optional.of(tasks.get(0));
            }
        }

        if (cheatTaskOptional.isPresent()) {
            if (cheatTaskOptional.get().getCostFor1000() < 200) {
                orderFollowers(bean, date, followersPerDay, cheatTaskOptional.get());
            } else {
                log.warn("Не заказываем очень дорогих подписчиков в эконом тарифе:" + cheatTaskOptional.get());
            }
        } else {
            log.warn("No Followers Cheat Tasks");
        }
    }
}
