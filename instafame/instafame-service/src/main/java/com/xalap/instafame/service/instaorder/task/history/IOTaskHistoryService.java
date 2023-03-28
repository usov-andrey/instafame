/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.history;

import com.xalap.framework.data.CrudService;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Усов Андрей
 * @since 22/04/2019
 */
@Service
public class IOTaskHistoryService extends CrudService<IOTaskHistoryBean, IOTaskHistoryRepository, Integer> {

    public IOTaskHistoryService() {
        super();
    }

    public void addHistory(IOTaskBean bean, int newPercent) {
        IOTaskHistoryBean historyBean = new IOTaskHistoryBean();
        historyBean.setCreateTime(new Date());
        historyBean.setPercent(newPercent);
        historyBean.setTask(bean);
        save(historyBean);
    }

    public Map<IOTaskBean, List<IOTaskHistoryBean>> getHistoryMap(List<IOTaskBean> tasks) {
        List<IOTaskHistoryBean> byTasks = repository().findByTasks(tasks);
        Map<IOTaskBean, List<IOTaskHistoryBean>> result = new HashMap<>();
        for (IOTaskHistoryBean byTask : byTasks) {
            result.computeIfAbsent(byTask.getTask(), (key) -> new ArrayList<>()).add(byTask);
        }
        return result;
    }

}
