/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.scheduler;

import com.xalap.crm.service.scheduler.log.SchedulerTaskLogBean;
import com.xalap.crm.service.scheduler.log.SchedulerTaskLogRepository;
import com.xalap.crm.service.scheduler.log.SchedulerTaskLogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Фоновое задание
 * @author Усов Андрей
 * @since 17/04/2019
 */
public abstract class SchedulerTask implements Runnable {

    protected static final Logger log = LoggerFactory.getLogger(SchedulerTask.class);

    @Autowired
    protected SchedulerService schedulerService;
    @Autowired
    private SchedulerTaskLogRepository logRepository;

    private final Date createTime = new Date();
    private SchedulerTaskBean bean;
    private boolean manualRun = false;

    public void setBean(SchedulerTaskBean bean) {
        this.bean = bean;
    }

    public void setManualRun() {
        manualRun = true;
    }

    @Override
    public void run() {
        bean = schedulerService.get(bean.getId());
        if (bean.isExecuting()) {
            log.info("Task is executing now already:" + bean);
            return;
        }
        log.debug("Start run task:" + getClass());
        SchedulerTaskLogBean logBean = startLog();
        try {
            doRun();
        } catch (Exception e) {
            log.error("Error on execute task:" + this, e);
            endLog(logBean, SchedulerTaskLogStatus.error);
        } finally {
            endLog(logBean, SchedulerTaskLogStatus.ok);
        }
        log.debug("End run task:" + getClass());
    }

    @Transactional
    public SchedulerTaskLogBean startLog() {
        SchedulerTaskLogBean logBean = new SchedulerTaskLogBean();
        logBean.setTask(bean);
        logBean.setStartTime(new Date());
        logBean.setStatus(SchedulerTaskLogStatus.executing);
        logBean = logRepository.save(logBean);

        bean.setLastLog(logBean);
        schedulerService.save(bean);
        return logBean;
    }

    @Transactional
    public void endLog(SchedulerTaskLogBean logBean, SchedulerTaskLogStatus status) {
        logBean.setEndTime(new Date());
        logBean.setStatus(status);
        logRepository.save(logBean);
        //Возможно за время выполнения задачу выключили, тогда не нужно ее включать
        bean = schedulerService.get(bean.getId());
        if (bean.getStatus() != SchedulerTaskStatus.disabled) {
            bean.setStatus(SchedulerTaskStatus.enabled);
        }
        bean.setLastExecutionTime(logBean.getStartTime());
        schedulerService.save(bean);
    }

    abstract protected void doRun();

    @Override
    public String toString() {
        return "SchedulerTask{" +
                ", createTime=" + createTime +
                ", bean=" + bean +
                ", manualRun=" + manualRun +
                '}';
    }
}
