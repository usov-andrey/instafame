/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.direct.service;

import com.xalap.crm.service.scheduler.SchedulerTask;
import com.xalap.framework.exception.RunnableWithServiceException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 06/06/2019
 */
public class UpdateInstagramMessagesTask extends SchedulerTask {

    @Autowired
    private InstagramDirectServiceImpl directService;

    @Override
    protected void doRun() {
        RunnableWithServiceException.run(() -> directService.updateAllPendingThreads());
        RunnableWithServiceException.run(() -> directService.updateAllMessages());
    }
}
