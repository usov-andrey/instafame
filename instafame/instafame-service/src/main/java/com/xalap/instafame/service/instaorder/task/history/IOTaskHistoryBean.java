/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.history;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Entity()
@Table(name = IOTaskHistoryBean.NAME)
public class IOTaskHistoryBean implements IdHolder<Integer> {

    public static final String NAME = "IS$IOTASKHISTORY";
    public static final String PERCENT = "percent";
    public static final String CREATE_TIME = "createTime";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @FieldName(CREATE_TIME)
    private Date createTime;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private IOTaskBean task;
    @NotNull
    @FieldName(PERCENT)
    private Integer percent;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public IOTaskBean getTask() {
        return task;
    }

    public void setTask(IOTaskBean task) {
        this.task = task;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "IOTaskHistoryBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", task=" + task +
                ", percent=" + percent +
                '}';
    }
}
