/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
public abstract class CheatAutoRunner {

    protected static final Logger log = LoggerFactory.getLogger(CheatAutoRunner.class);
    protected static int PERIOD_HOURS = 24;//Проставляем лайки на окно в 24 часа

    protected IOTaskService service;
    protected IOTaskProvider taskProvider;

    public CheatAutoRunner(IOTaskService service, IOTaskProvider taskProvider) {
        this.service = service;
        this.taskProvider = taskProvider;
    }

    abstract public void run(IOBean bean);

    public void restore(IOBean parentBean) {

    }

    public void manualRestore(IOBean orderBean) {

    }
}
