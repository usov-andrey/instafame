/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat;

import com.xalap.crm.service.scheduler.SchedulerTask;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 08/07/2019
 */
public class UpdateCheatServiceTask extends SchedulerTask {

    @Autowired
    private CheatTaskService cheatTaskService;

    @Override
    protected void doRun() {
        cheatTaskService.updateTasks();
    }
}
