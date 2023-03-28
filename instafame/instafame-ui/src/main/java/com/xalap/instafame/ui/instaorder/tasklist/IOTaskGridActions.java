/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.function.SerializableRunnable;
import com.xalap.instafame.service.cheat.exception.ServiceAbortErrorException;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.vaadin.custom.grid.GridActions;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Усов Андрей
 * @since 25/07/2019
 */
public class IOTaskGridActions implements SerializableBiConsumer<GridActions<IOTaskBean>, IOTaskBean> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ServiceRef<IOTaskService> service;
    private final SerializableRunnable dataChangeHandler;

    public IOTaskGridActions(ServiceRef<IOTaskService> service, SerializableRunnable dataChangeHandler) {
        this.service = service;
        this.dataChangeHandler = dataChangeHandler;
    }

    @Override
    public void accept(GridActions<IOTaskBean> actions, IOTaskBean ioTaskBean) {
        if (ioTaskBean.getStatus() == InstaOrderTaskStatus.Created && ioTaskBean.getCheatTask() != null) {
            actions.add("Запустить", () -> {
                service.get().runTask(ioTaskBean);
                dataChangeHandler.run();
            });
        } else if (ioTaskBean.getStatus() == InstaOrderTaskStatus.InProgress) {
            actions.add("Прервать", () -> {
                try {
                    service.get().abortTask(ioTaskBean);
                } catch (ServiceAbortErrorException e) {
                    log.warn("Error on cancel task:" + ioTaskBean, e);
                    UIUtils.showNotification("Произошла ошибка при отмене, возможно через некоторое время " +
                            "заказ отменится");
                }
                dataChangeHandler.run();
            });
        } else if (ioTaskBean.getStatus() == InstaOrderTaskStatus.ManualCheck) {
            actions.add("Отменить", () -> {
                service.get().cancelTask(ioTaskBean);
                dataChangeHandler.run();
            });
        }
        if (ioTaskBean.getExtOrderId() != null) {
            actions.add("Обновить", () -> {
                service.get().updateTask(ioTaskBean);
                dataChangeHandler.run();
            });
        }
        if (ioTaskBean.getCheatTask() != null) {
            if (ioTaskBean.refillable()) {
                actions.add("Refill", () -> {
                    service.get().refill(ioTaskBean);
                    dataChangeHandler.run();
                });

            }

        }
        actions.add("Удалить", () -> {
            service.get().delete(ioTaskBean);
            dataChangeHandler.run();
        });
    }

}
