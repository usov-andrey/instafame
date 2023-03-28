/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;

/**
 * @author Усов Андрей
 * @since 26/11/2019
 */
public class RuPremiumFollowersPackageRunner extends PremiumFollowersPackageRunner {

    public RuPremiumFollowersPackageRunner(IOTaskService service, IOTaskProvider taskProvider) {
        super(service, taskProvider);
    }
}
