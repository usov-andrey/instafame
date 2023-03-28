/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order;

import com.xalap.crm.service.scheduler.SchedulerTask;
import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Usov Andrey
 * @since 2020-06-04
 */
public class ForgotOrderNotificationSchedulerTask extends SchedulerTask {

    @Autowired
    private OrderService orderService;
    @Autowired
    private NotificationService notificationService;

    public ForgotOrderNotificationSchedulerTask() {
    }

    @Override
    protected void doRun() {
        Date from = DateHelper.incDays(new Date(), -1);
        if (orderService.haveNotPayOrders(from)) {
            notificationService.sendMessage("Есть неоплаченные заказы с момента " + DateHelper.getDateTime(from));
        }
    }


}
