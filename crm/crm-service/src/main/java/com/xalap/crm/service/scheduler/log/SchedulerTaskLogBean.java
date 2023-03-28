/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.scheduler.log;

import com.xalap.crm.service.scheduler.SchedulerTaskBean;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.utils.DateHelper;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Entity(name = SchedulerTaskLogBean.NAME)
public class SchedulerTaskLogBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$TASKLOG";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String STATUS = "status";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SchedulerTaskBean task;
    @FieldName(START_TIME)
    private Date startTime;
    @FieldName(END_TIME)
    private Date endTime;
    @FieldName(STATUS)
    private SchedulerTaskLogStatus status;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public SchedulerTaskBean getTask() {
        return task;
    }

    public void setTask(SchedulerTaskBean task) {
        this.task = task;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public SchedulerTaskLogStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulerTaskLogStatus status) {
        this.status = status;
    }

    public String duration() {
        return endTime != null ? DateHelper.formatDuration(startTime, endTime) : "";
    }

    @Override
    public String toString() {
        return "SchedulerTaskLogBean{" +
                "id=" + id +
                ", task=" + task +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
