/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Entity()
@Table(name = IOTaskBean.NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "IOT_TYPE")
public class IOTaskBean implements IdHolder<Integer> {

    public static final String NAME = "IS$INSTAORDERTASK";
    public static final String CREATE_TIME = "createTime";
    public static final String TASK_TYPE = "taskType";
    public static final String QUANTITY = "quantity";
    public static final String START_COUNT = "startCount";
    public static final String REMAINS = "remains";
    public static final String COUNT_SPEED = "countSpeed";
    public static final String TIME_UNIT_SPEED = "timeUnitSpeed";
    public static final String CHARGE = "charge";
    public static final String STATUS = "status";
    public static final String ORDER = "order";
    public static final String EXT_ORDER_ID = "extOrderId";
    public static final String SEND_TIME = "sendTime";
    public static final String COMPLETE_TIME = "completeTime";
    public static final String REFILL_TIME = "refillTime";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @FieldName(CREATE_TIME)
    @NotNull
    private Date createTime;
    @FieldName(TASK_TYPE)
    @NotNull
    private InstaOrderTaskType taskType;
    @FieldName(QUANTITY)
    @NotNull
    private int quantity;
    @FieldName(START_COUNT)
    private Integer startCount;
    @FieldName(REMAINS)
    private Integer remains;
    @FieldName(COUNT_SPEED)
    private Integer countSpeed;
    @FieldName(TIME_UNIT_SPEED)
    private TimeUnit timeUnitSpeed;

    @FieldName(CHARGE)
    private Double charge;
    @FieldName(STATUS)
    @NotNull
    private InstaOrderTaskStatus status;
    @FieldName(ORDER)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private @NotNull IOBean order;
    @FieldName(EXT_ORDER_ID)
    private String extOrderId;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CheatTaskBean cheatTask;
    @FieldName(SEND_TIME)
    private Date sendTime;//Время отправки во внешнюю систему
    @FieldName(COMPLETE_TIME)
    private Date completeTime;
    private Integer errorAttemptCount;
    @ColumnDefault("true")
    private boolean notifyOnDelay;
    @FieldName(REFILL_TIME)
    private Date refillTime;

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

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getRemains() {
        return remains;
    }

    public void setRemains(Integer remains) {
        this.remains = remains;
    }

    public IOBean getOrder() {
        return order;
    }

    public void setOrder(IOBean order) {
        this.order = order;
    }

    public InstaOrderTaskStatus getStatus() {
        return status;
    }

    public void setStatus(InstaOrderTaskStatus status) {
        this.status = status;
    }

    public Integer getCountSpeed() {
        return countSpeed;
    }

    public void setCountSpeed(Integer countSpeed) {
        this.countSpeed = countSpeed;
    }

    public TimeUnit getTimeUnitSpeed() {
        return timeUnitSpeed;
    }

    public void setTimeUnitSpeed(TimeUnit timeUnitSpeed) {
        this.timeUnitSpeed = timeUnitSpeed;
    }

    public InstaOrderTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(InstaOrderTaskType taskType) {
        this.taskType = taskType;
    }

    public String getExtOrderId() {
        return extOrderId;
    }

    public void setExtOrderId(String extOrderId) {
        this.extOrderId = extOrderId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isLikes() {
        return taskType == InstaOrderTaskType.likes;
    }

    public boolean isComments() {
        return taskType == InstaOrderTaskType.comments;
    }

    public boolean isFollowers() {
        return taskType == InstaOrderTaskType.followers;
    }

    public <T extends IOTaskBean> T cast() {
        return (T) this;
    }

    public boolean InProgressAndRemains() {
        return status == InstaOrderTaskStatus.InProgress && getRemains() != null && getRemains() > 0;
    }

    public int quantityMinusRemains() {
        return status != InstaOrderTaskStatus.Completed ? (getQuantity() - (getRemains() != null ? getRemains() : 0)) : getQuantity();
    }

    public String caption() {
        return (cheatTask != null ? (cheatTask.getProviderName() + ":") : "") +
                quantity + "." + id;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public CheatTaskBean getCheatTask() {
        return cheatTask;
    }

    public void setCheatTask(CheatTaskBean cheatTask) {
        this.cheatTask = cheatTask;
    }

    public Integer getErrorAttemptCount() {
        return errorAttemptCount;
    }

    public void setErrorAttemptCount(Integer errorAttemptCount) {
        this.errorAttemptCount = errorAttemptCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IOTaskBean that = (IOTaskBean) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public long runServiceDuration() {
        Date endTime = getCompleteTime() != null ? getCompleteTime() : new Date();
        return DateHelper.duration(sendTime(), endTime);

    }

    public String duration() {
        return DateHelper.formatDuration(runServiceDuration());
    }

    public Date sendTime() {
        return getSendTime() != null ? getSendTime() : new Date(1);
    }

    public boolean isNotifyOnDelay() {
        return notifyOnDelay;
    }

    public void setNotifyOnDelay(boolean notifyOnDelay) {
        this.notifyOnDelay = notifyOnDelay;
    }

    @Override
    public String toString() {
        return "IOTaskBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", taskType=" + taskType +
                ", quantity=" + quantity +
                ", startCount=" + startCount +
                ", remains=" + remains +
                ", countSpeed=" + countSpeed +
                ", timeUnitSpeed=" + timeUnitSpeed +
                ", charge=" + charge +
                ", status=" + status +
                ", order=" + order +
                ", extOrderId='" + extOrderId + '\'' +
                ", cheatTask=" + cheatTask +
                ", sendTime=" + sendTime +
                ", completeTime=" + completeTime +
                ", errorAttemptCount=" + errorAttemptCount +
                '}';
    }

    public Date getRefillTime() {
        return refillTime;
    }

    public void setRefillTime(Date refillTime) {
        this.refillTime = refillTime;
    }

    public boolean refillable() {
        int refillDays = getCheatTask().getRefillDays();
        return getStatus() == InstaOrderTaskStatus.Completed && refillDays > 0
                && DateHelper.incDays(getCreateTime(), refillDays).after(new Date());
    }
}
