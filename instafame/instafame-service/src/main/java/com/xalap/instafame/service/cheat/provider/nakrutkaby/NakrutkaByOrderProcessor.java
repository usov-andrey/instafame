/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.nakrutkaby;

import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;

import java.util.Date;

/**
 * @author Usov Andrey
 * @since 21.05.2021
 */
public class NakrutkaByOrderProcessor {

    public void updateOrder(IOTaskBean bean, NakrutkaByOrderStatus order) {
        bean.setCharge(Double.parseDouble(order.getCharge()));
        bean.setRemains(Integer.parseInt(order.getRemains()));
        bean.setStartCount(Integer.parseInt(order.getStartCount()));
        if (order.getStatus().equals("Completed")) {
            bean.setStatus(InstaOrderTaskStatus.Completed);
        } else if (order.getStatus().equals("Partial") || order.getStatus().equals("Canceled")) {
            bean.setStatus(InstaOrderTaskStatus.Interrupted);
        } else //Если прошло более суток, то переводим задачу на простановку комментария в статус Interrupted
            if (bean.isComments() && (bean.getSendTime() == null || DateHelper.daysBetweenDates(bean.getSendTime(), new Date()) > 0)) {
                bean.setStatus(InstaOrderTaskStatus.Interrupted);
            }
    }

}
