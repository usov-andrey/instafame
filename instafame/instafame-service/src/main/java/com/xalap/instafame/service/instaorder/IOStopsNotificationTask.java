/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.crm.service.scheduler.SchedulerTask;
import com.xalap.framework.notification.NotificationService;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryProvider;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryService;
import com.xalap.instafame.service.instaorder.task.provider.MemoryIOTaskProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Отправляем уведомление в случае остановки заказа
 *
 * @author Usov Andrey
 * @since 2020-09-30
 */
public class IOStopsNotificationTask extends SchedulerTask {

    @Autowired
    private IOService ioService;
    @Autowired
    private IOTaskHistoryService historyService;
    @Autowired
    private IOTaskService ioTaskService;
    @Autowired
    private NotificationService notificationService;

    @Override
    protected void doRun() {
        List<IOBean> notCompleted = ioService.getNotCompleted();
        MemoryIOTaskProvider ioTaskProvider = ioTaskService.memoryTaskProvider(notCompleted);
        List<IOTaskBean> tasks = ioTaskProvider.getTasks(InstaOrderTaskStatus.InProgress);
        IOTaskHistoryProvider historyProvider = new IOTaskHistoryProvider();
        historyProvider.setValue(historyService, tasks);
        //Если есть задержка больше 8 часов, то отправляем уведомление
        if (historyProvider.haveDelayHours(ioService.getMaxDelayHours())) {
            notificationService.sendMessage("Остановка выполнения заказа составляет больше " + ioService.getMaxDelayHours());
        }
    }


}
