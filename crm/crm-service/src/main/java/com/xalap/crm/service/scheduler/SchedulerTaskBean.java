/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.scheduler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xalap.crm.service.scheduler.log.SchedulerTaskLogBean;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdNameHolder;
import com.xalap.framework.spring.BeanDependencyInjector;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Entity(name = SchedulerTaskBean.NAME)
public class SchedulerTaskBean implements IdNameHolder<Integer> {

    public static final String NAME = "CRM$TASK";
    public static final String NAME1 = "name";
    public static final String TIME = "time";
    public static final String PERIOD = "period";
    public static final String PERIOD_VALUE = "periodValue";
    public static final String STATUS = "status";
    public static final String LAST_EXEC_TIME = "lastExecTime";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @NotNull
    @FieldName(NAME1)
    private String name;
    @NotNull
    @FieldName(TIME)
    private Date time;
    private String taskClassName;
    @FieldName(PERIOD)
    private SchedulerTaskPeriod period;
    @FieldName(PERIOD_VALUE)
    private Integer periodValue;
    @FieldName(STATUS)
    private SchedulerTaskStatus status;
    @FieldName(LAST_EXEC_TIME)
    private Date lastExecutionTime;
    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SchedulerTaskLogBean lastLog;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskClassName() {
        return taskClassName;
    }

    public void setTaskClassName(String taskClassName) {
        this.taskClassName = taskClassName;
    }

    public SchedulerTaskPeriod getPeriod() {
        return period;
    }

    public void setPeriod(SchedulerTaskPeriod period) {
        this.period = period;
    }

    public SchedulerTaskStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulerTaskStatus status) {
        this.status = status;
    }

    public Integer getPeriodValue() {
        return periodValue;
    }

    public void setPeriodValue(Integer periodValue) {
        this.periodValue = periodValue;
    }

    public Date getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(Date lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }

    public SchedulerTaskLogBean getLastLog() {
        return lastLog;
    }

    public void setLastLog(SchedulerTaskLogBean lastLog) {
        this.lastLog = lastLog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Возвращаем периодичность в милисекундах
     * Нельзя использовать данный метод для определения предыдущего запуска задачи!!!
     */
    public long periodInMs() {
        return getPeriod().getTime(getPeriodValue());
    }

    /**
     * @return выражение cron для выполнения по расписанию
     */
    public String cronExpression() {
        if (everyDayOfMonth()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            int minute = calendar.get(Calendar.MINUTE);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            return String.format("0 %d %d %d * *", minute, hour, dayOfMonth); //выполнить в 0 секунд, minute минут, hour часов, dayOfMonth числа каждого месяца каждого года
        } else {
            return null;
        }
    }

    private boolean everyDayOfMonth() {
        return SchedulerTaskPeriod.every_day_of_month.equals(period);
    }

    /**
     * Время первого запуска задания после текущего времени
     *
     * @return Можешь возвращать null, если в планах нет запуска текущей задачи
     */
    public Date firstExecuteTimeAfterNow() {
        return firstExecuteTimeAfterDate(new Date());
    }

    /**
     * Время первого запуска задания после указанного времени
     */
    public Date firstExecuteTimeAfterDate(Date testDate) {
        //Для просчета первого запуска после указанной даты для периода по месяцам
        // периоды между разными месяцами могут отличатся
        if (everyDayOfMonth()) {
            if (getTime().after(testDate)) {
                return getTime();
            } else {
                Date firstTime = new Date(getTime().getTime());
                firstTime.setMonth(testDate.getMonth());
                firstTime.setYear(testDate.getYear());
                if (firstTime.before(testDate)) {
                    firstTime.setMonth(testDate.getMonth() + 1);
                }
                return firstTime;
            }
        } else {
            long periodMS = periodInMs();
            if (periodMS == 0) {
                Date date = getTime();
                if (date.after(testDate)) {
                    return date;
                } else {
                    return null;
                }
            } else {
                long curTime = testDate.getTime();
                long time = getTime().getTime();
                while (time <= curTime) {
                    time += periodMS;
                }
                return new Date(time);
            }
        }
    }

    /**
     * Одиночный запуск
     */
    public boolean isOnceExecute() {
        return SchedulerTaskPeriod.one_time == period;
    }


    public boolean isEnabled() {
        return status == SchedulerTaskStatus.enabled;
    }

    public boolean isExecuting() {
        return status == SchedulerTaskStatus.executing;
    }

    @Override
    public String toString() {
        return "SchedulerTaskBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", taskClassName='" + taskClassName + '\'' +
                ", period=" + period +
                ", periodValue=" + periodValue +
                ", status=" + status +
                ", lastExecutionTime=" + lastExecutionTime +
                '}';
    }

    public SchedulerTask createTask(BeanDependencyInjector dependencyInjector) {
        SchedulerTask task = dependencyInjector.create(getTaskClassName());
        task.setBean(this);
        return task;
    }
}
