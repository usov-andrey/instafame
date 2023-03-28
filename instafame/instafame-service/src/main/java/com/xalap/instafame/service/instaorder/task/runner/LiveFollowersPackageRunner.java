/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;

/**
 * @author Usov Andrey
 * @since 05/02/2020
 */
public class LiveFollowersPackageRunner extends PremiumFollowersPackageRunner {

    public LiveFollowersPackageRunner(IOTaskService service, IOTaskProvider taskProvider) {
        super(service, taskProvider);
    }

    @Override
    public void restore(IOBean parentBean) {
        //Не восстанавливаем
    }
}
