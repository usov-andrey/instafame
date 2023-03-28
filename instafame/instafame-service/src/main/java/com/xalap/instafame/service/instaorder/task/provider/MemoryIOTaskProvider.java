/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.provider;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 18/05/2019
 */
public class MemoryIOTaskProvider implements IOTaskProvider {

    protected List<IOTaskBean> allTasks;

    public MemoryIOTaskProvider() {

    }

    public MemoryIOTaskProvider(List<IOTaskBean> allTasks) {
        setTasks(allTasks);
    }

    public void setTasks(List<IOTaskBean> allTasks) {
        this.allTasks = allTasks;
        sortAllTasks();
    }

    private void sortAllTasks() {
        Collections.sort(allTasks, Comparator.comparing(IOTaskBean::getCreateTime));
    }

    private <T extends IOTaskBean> List<T> tasks(Predicate<IOTaskBean> predicate) {
        return (List<T>) allTasks.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public <T extends IOTaskBean> List<T> getTasks(IOBean orderBean, InstaOrderTaskType taskType, InstaOrderTaskStatus status) {
        return tasks(taskBean -> taskBean.getOrder().equals(orderBean) && taskBean.getTaskType() == taskType && taskBean.getStatus() == status);
    }

    @Override
    public List<IOTaskBean> getTasks(IOBean orderBean) {
        return tasks(taskBean -> taskBean.getOrder().equals(orderBean));
    }

    @Override
    public <T extends IOTaskBean> List<T> getTasks(IOBean orderBean, InstaOrderTaskType taskType) {
        return tasks(taskBean -> taskBean.getOrder().equals(orderBean) && taskBean.getTaskType() == taskType);
    }

    @Override
    public <T extends IOTaskBean> List<T> getTasks(String instagram, InstaOrderTaskType taskType, InstaOrderTaskStatus status) {
        return tasks(taskBean -> taskBean.getOrder().getInstagram() != null &&
                taskBean.getOrder().getInstagram().equals(instagram)
                && taskBean.getTaskType() == taskType && taskBean.getStatus() == status);
    }

    @Override
    public List<IOTaskBean> getTasks(IOBean orderBean, InstaOrderTaskStatus... statuses) {
        return tasks(taskBean -> {
            if (taskBean.getOrder().equals(orderBean)) {
                for (InstaOrderTaskStatus status : statuses) {
                    if (taskBean.getStatus() == status) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    @Override
    public List<IOTaskBean> getTasks(InstaOrderTaskStatus... statuses) {
        return tasks(taskBean -> {
            for (InstaOrderTaskStatus status : statuses) {
                if (taskBean.getStatus() == status) {
                    return true;
                }
            }
            return false;
        });
    }
}
