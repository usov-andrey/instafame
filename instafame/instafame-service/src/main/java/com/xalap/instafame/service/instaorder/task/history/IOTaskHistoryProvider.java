/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.history;

import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 28/07/2019
 */
public class IOTaskHistoryProvider implements Serializable {

    private Map<IOTaskBean, List<IOTaskHistoryBean>> value;

    public Date lastHistoryTime(IOTaskBean taskBean) {
        List<IOTaskHistoryBean> historyBeanList = value.get(taskBean);
        if (historyBeanList != null && !historyBeanList.isEmpty()) {
            Optional<IOTaskHistoryBean> max = historyBeanList.stream()
                    .max((o1, o2) -> o1.getPercent().compareTo(o2.getPercent()));
            if (max.isPresent()) {
                return max.get().getCreateTime();
            }
        }
        return taskBean.getSendTime();
    }

    public Optional<IOTaskHistoryBean> firstHistory(IOTaskBean taskBean) {
        List<IOTaskHistoryBean> historyBeanList = value.get(taskBean);
        if (historyBeanList != null && !historyBeanList.isEmpty()) {
            return historyBeanList.stream()
                    .min((o1, o2) -> o1.getPercent().compareTo(o2.getPercent()));
        }
        return Optional.empty();
    }

    public void setValue(IOTaskHistoryService historyService, List<IOTaskBean> tasks) {
        this.value = historyService.getHistoryMap(tasks);
    }

    /**
     * @return Есть задержка(нет никаких движений по прогрессу) больше чем hours или нет
     */
    public boolean haveDelayHours(int hours) {
        for (IOTaskBean task : value.keySet()) {
            if (task.isNotifyOnDelay()) {
                Date date = lastHistoryTime(task);
                if (DateHelper.hoursBetweenDates(date, new Date()) >= hours) {
                    return true;
                }
            }
        }
        return false;
    }
}
