/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.provider;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskRepository;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;

import java.util.List;

/**
 * Данные по одному заказу
 *
 * @author Усов Андрей
 * @since 18/05/2019
 */
public class MemoryOrderIOTaskProvider extends MemoryIOTaskProvider {

    private final IOTaskRepository repository;
    private IOBean orderBean;

    public MemoryOrderIOTaskProvider(IOTaskRepository repository, IOBean orderBean) {
        this(repository);
        setTaskList(orderBean);
    }

    public MemoryOrderIOTaskProvider(IOTaskRepository repository) {
        super();
        this.repository = repository;
    }

    public void setTaskList(IOBean orderBean) {
        setTasks(repository.findByOrder(orderBean));
        this.orderBean = orderBean;
    }

    @Override
    public <T extends IOTaskBean> List<T> getTasks(IOBean orderBean, InstaOrderTaskType taskType, InstaOrderTaskStatus status) {
        if (!orderBean.equals(this.orderBean)) {
            throw new IllegalStateException("Different order beans: " + orderBean + " and " + this.orderBean);
        }
        return super.getTasks(orderBean, taskType, status);
    }

    @Override
    public List<IOTaskBean> getTasks(IOBean orderBean) {
        if (!orderBean.equals(this.orderBean)) {
            throw new IllegalStateException("Different order beans: " + orderBean + " and " + this.orderBean);
        }
        return allTasks;
    }

    @Override
    public <T extends IOTaskBean> List<T> getTasks(IOBean orderBean, InstaOrderTaskType taskType) {
        if (!orderBean.equals(this.orderBean)) {
            throw new IllegalStateException("Different order beans: " + orderBean + " and " + this.orderBean);
        }
        return super.getTasks(orderBean, taskType);
    }

    @Override
    public <T extends IOTaskBean> List<T> getTasks(String instagram, InstaOrderTaskType taskType, InstaOrderTaskStatus status) {
        return (List<T>) repository.findByInstagramAndTaskTypeAndStatus(instagram, taskType, status);
    }

    @Override
    public List<IOTaskBean> getTasks(IOBean orderBean, InstaOrderTaskStatus... status) {
        if (!orderBean.equals(this.orderBean)) {
            throw new IllegalStateException("Different order beans: " + orderBean + " and " + this.orderBean);
        }
        return super.getTasks(orderBean, status);
    }
}
