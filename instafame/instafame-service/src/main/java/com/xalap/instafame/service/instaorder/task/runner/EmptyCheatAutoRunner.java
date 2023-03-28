/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;

/**
 * @author Усов Андрей
 * @since 09/05/2019
 */
public class EmptyCheatAutoRunner extends CheatAutoRunner {

    public EmptyCheatAutoRunner(IOTaskService service, IOTaskProvider taskProvider) {
        super(service, taskProvider);
    }

    @Override
    public void run(IOBean bean) {

    }
}
