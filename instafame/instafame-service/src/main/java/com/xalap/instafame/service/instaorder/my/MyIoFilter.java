/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.my;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.filter.SearchFilter;

/**
 * @author Usov Andrey
 * @since 2020-05-14
 */
public class MyIoFilter extends SearchFilter {

    public static final String TYPE = "type";
    public static final String STATUS = "status";
    @FieldName(TYPE)
    private MyIoType taskType = MyIoType.all;
    @FieldName(STATUS)
    private MyIoStatus status = MyIoStatus.all;

    public MyIoType getTaskType() {
        return taskType;
    }

    public void setTaskType(MyIoType taskType) {
        this.taskType = taskType;
    }

    public MyIoStatus getStatus() {
        return status;
    }

    public void setStatus(MyIoStatus status) {
        this.status = status;
    }

    @Override
    public boolean empty() {
        return taskType == MyIoType.all && status == MyIoStatus.all && super.empty();
    }
}
