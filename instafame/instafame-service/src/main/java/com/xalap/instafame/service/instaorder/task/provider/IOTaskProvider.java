/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.provider;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 18/05/2019
 */
public interface IOTaskProvider {

    List<IOTaskBean> getTasks(IOBean parentBean);

    <T extends IOTaskBean> List<T> getTasks(IOBean parentBean, InstaOrderTaskType taskType);

    <T extends IOTaskBean> List<T> getTasks(IOBean parentBean, InstaOrderTaskType taskType, InstaOrderTaskStatus status);

    <T extends IOTaskBean> List<T> getTasks(String instagram, InstaOrderTaskType taskType, InstaOrderTaskStatus status);

    List<IOTaskBean> getTasks(IOBean parentBean, InstaOrderTaskStatus... status);

    List<IOTaskBean> getTasks(InstaOrderTaskStatus... status);

}
