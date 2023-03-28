/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.scheduler;

import com.xalap.crm.service.scheduler.log.SchedulerTaskLogBean;
import com.xalap.crm.service.scheduler.log.SchedulerTaskLogRepository;
import com.xalap.crm.service.scheduler.log.SchedulerTaskLogStatus;
import com.xalap.framework.data.CrudService;
import com.xalap.framework.integration.lock.LockService;
import com.xalap.framework.integration.lock.LockableRunnable;
import com.xalap.framework.spring.BeanDependencyInjector;
import com.xalap.framework.task.ThreadNameChangeRunnable;
import com.xalap.framework.utils.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * TODO Для работы в кластере нужно убрать tasks и перевести на https://github.com/lukas-krecan/ShedLock
 * TODO Может быть стоит для этого использовать Spring Batch
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Service
public class SchedulerService extends CrudService<SchedulerTaskBean, SchedulerTaskRepository, Integer> {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
    private final Map<Integer, Future<?>> tasks = new ConcurrentHashMap<>();//Выполняемые на текущий момент задачи
    private final Set<String> taskClasses = new HashSet<>();
    private final BeanDependencyInjector dependencyInjector;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final SchedulerTaskLogRepository logRepository;
    private final LockService lockService;

    public SchedulerService(BeanDependencyInjector dependencyInjector, ThreadPoolTaskScheduler taskScheduler, SchedulerTaskLogRepository logRepository, LockService lockService) {
        super();
        this.dependencyInjector = dependencyInjector;
        this.taskScheduler = taskScheduler;
        this.logRepository = logRepository;
        this.lockService = lockService;
    }

    /**
     * Регистрация задач запускаемых через пользовательский интерфейс
     */
    public void register(Class<? extends SchedulerTask> taskClass, String name) {
        if (taskScheduler == null) {
            return;
        }
        String taskClassName = taskClass.getName();
        taskClasses.add(taskClassName);
        SchedulerTaskBean bean = repository().findByTaskClassName(taskClassName);
        if (bean == null) {
            log.debug("Регистрируем задачу в базе первый раз:" + taskClass);
            bean = new SchedulerTaskBean();
            bean.setTime(new Date());
            bean.setStatus(SchedulerTaskStatus.disabled);
            bean.setName(name);
            bean.setPeriod(SchedulerTaskPeriod.one_time);
            bean.setTaskClassName(taskClassName);
            save(bean);
        } else {
            SchedulerTaskLogBean lastLog = bean.getLastLog();
            if (lastLog != null && lastLog.getStatus() == SchedulerTaskLogStatus.executing) {
                log.debug("Выполнение задачи было прервано остановкой сервера, запускаем задачу заново:" + bean);
                //Значит задача выполнялась в момент остановки приложения, нужно изменить статус у задачи и в журнале выполнения
                lastLog.setEndTime(new Date());
                lastLog.setStatus(SchedulerTaskLogStatus.aborted);
                logRepository.save(lastLog);
            }
            if (bean.isEnabled()) {
                scheduleTask(bean);
            }
        }
    }

    public void scheduleTask(SchedulerTaskBean taskBean) {
        //На всякий случай отсекаем некорректный задачи
        if (!taskBean.isEnabled()) {
            log.debug("Задача не включена:" + taskBean);
            return;
        }

        Date firstExecuteTime = taskBean.firstExecuteTimeAfterNow();
        if (firstExecuteTime == null) {
            log.debug("Время первого запуска задачи не задано: " + taskBean);
            return;
        }

        long periodInMS = taskBean.periodInMs();
        SchedulerTask task = taskBean.createTask(dependencyInjector);
        Runnable taskRunnable = createTaskRunnable(task, taskBean);
        if (taskBean.isOnceExecute()) {
            tasks.put(taskBean.getId(), taskScheduler.schedule(taskRunnable, firstExecuteTime));
            log.debug(
                    String.format("ScheduledTask {id=%s} was scheduled with parameters: firstExecuteTime=%s in executor:%s",
                            taskBean.getId() + task.getClass().getSimpleName(), firstExecuteTime, executorInfo()));
        } else {
            if (tasks.containsKey(taskBean.getId())) {
                throw new IllegalStateException("Can't shedule task: " + taskBean + " Task already scheduled:" + tasks.get(taskBean.getId()));
            }
            String cronExpression = taskBean.cronExpression();
            if (StringHelper.isNotEmpty(cronExpression)) {
                tasks.put(taskBean.getId(), taskScheduler.schedule(taskRunnable, new CronTrigger(cronExpression)));
                log.debug(
                        String.format("ScheduledTask {id=%s} was scheduled with parameters: cronExpression=%s in executor:%s",
                                taskBean.getId() + task.getClass().getSimpleName(), cronExpression, executorInfo()));
            } else {
                tasks.put(taskBean.getId(), taskScheduler.scheduleAtFixedRate(taskRunnable, firstExecuteTime, periodInMS));
                log.debug(
                        String.format("ScheduledTask {id=%s} was scheduled with parameters: firstExecuteTime=%s, periodInMS=%s in executor:%s",
                                taskBean.getId() + task.getClass().getSimpleName(), firstExecuteTime, periodInMS, executorInfo()));
            }
        }
    }

    private String executorInfo() {
        return taskScheduler.toString();
    }

    public void runTask(SchedulerTaskBean taskBean) {
        log.debug("Run schedule task: " + taskBean + " in executor:" + executorInfo());
        SchedulerTask task = taskBean.createTask(dependencyInjector);
        task.setManualRun();
        taskScheduler.execute(createTaskRunnable(task, taskBean));
    }

    private Runnable createTaskRunnable(SchedulerTask task, SchedulerTaskBean taskBean) {
        return new LockableRunnable(new ThreadNameChangeRunnable(task, "Scheduler." + task.getClass().getSimpleName()
                + "." + taskBean.getId()), taskBean.idToString(), lockService);
    }

    private void unScheduleTask(Integer schedulerTaskId) {
        Future<?> timerTask = tasks.get(schedulerTaskId);
        if (timerTask != null) {
            log.debug("Cancelling task: " + schedulerTaskId);
            timerTask.cancel(true);
            tasks.remove(schedulerTaskId);
        }
    }

    public void reSchedule(SchedulerTaskBean bean) {
        unScheduleTask(bean.getId());
        if (bean.isEnabled()) {
            scheduleTask(bean);
        }
    }

    @Override
    public SchedulerTaskBean save(SchedulerTaskBean bean) {
        if (bean.getId() != null) {
            SchedulerTaskBean oldBean = get(bean.getId());
            bean = super.save(bean);
            if (oldBean.getPeriod() != bean.getPeriod() ||
                    !oldBean.getTime().equals(bean.getTime()) ||
                    !Objects.equals(oldBean.getPeriodValue(), bean.getPeriodValue())) {
                reSchedule(bean);
            } else if (bean.getStatus() == SchedulerTaskStatus.enabled && oldBean.getStatus() == SchedulerTaskStatus.disabled) {
                scheduleTask(bean);
            } else if (bean.getStatus() == SchedulerTaskStatus.disabled && oldBean.getStatus() == SchedulerTaskStatus.enabled) {
                unScheduleTask(bean.getId());
            }
        } else {
            bean = super.save(bean);
            if (bean.getStatus() == SchedulerTaskStatus.enabled) {
                scheduleTask(bean);
            }
        }
        return bean;
    }

    @Override
    public void delete(SchedulerTaskBean bean) {
        unScheduleTask(bean.getId());
        super.delete(bean);
    }

    @PreDestroy
    public void shoutDown() {
        for (Integer schedulerTaskId : tasks.keySet()) {
            log.debug("Cancelling task: " + schedulerTaskId);
            tasks.get(schedulerTaskId).cancel(true);
            tasks.remove(schedulerTaskId);
        }
    }

    public boolean isRegisteredTask(SchedulerTaskBean taskBean) {
        return taskClasses.contains(taskBean.getTaskClassName());
    }
}
