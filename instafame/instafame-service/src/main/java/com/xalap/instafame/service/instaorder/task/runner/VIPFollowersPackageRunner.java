/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;

import java.util.Optional;

/**
 * Стратегия для VIP подписчиков
 *
 * @author Усов Андрей
 * @since 17/04/2019
 */
public class VIPFollowersPackageRunner extends PremiumFollowersPackageRunner {

    public VIPFollowersPackageRunner(IOTaskService service, IOTaskProvider taskProvider) {
        super(service, taskProvider);
    }

    @Override
    protected Optional<CheatTaskBean> getCheatTaskBean() {
        Optional<CheatTaskBean> vipFollowersTask = service.getSettings().getVipFollowersTask();
        return vipFollowersTask.isPresent() ? vipFollowersTask : super.getCheatTaskBean();
    }

    @Override
    public void restore(IOBean parentBean) {
        //VIP подписчиков пока что не восстанавливаем
    }
}
