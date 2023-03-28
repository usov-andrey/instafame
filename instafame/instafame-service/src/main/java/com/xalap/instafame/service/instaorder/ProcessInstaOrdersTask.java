/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.crm.service.scheduler.SchedulerTask;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Обработка insta заказов
 *
 * @author Усов Андрей
 * @since 09/05/2019
 */
public class ProcessInstaOrdersTask extends SchedulerTask {

    @Autowired
    private IOService instaOrderService;

    @Override
    protected void doRun() {
        instaOrderService.changeIoStatuses();
        instaOrderService.updateAllTasksProgress();
        instaOrderService.addAutoTasks();
        instaOrderService.runAllOrders();
    }
}
