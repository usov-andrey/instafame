/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat;

import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.framework.data.CrudService;
import com.xalap.framework.exception.ConsumerWithServiceException;
import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.integration.lock.LockService;
import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.instafame.service.cheat.provider.CheatTaskProvider;
import com.xalap.instafame.service.cheat.provider.CheatTaskProviderBean;
import com.xalap.instafame.service.cheat.provider.CheatTaskProviderService;
import com.xalap.instafame.service.instaorder.settings.InstaOrderSettingsService;
import com.xalap.instafame.service.instaorder.task.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Service
public class CheatTaskService extends CrudService<CheatTaskBean, CheatTaskRepository, Integer> {

    private static final Logger log = LoggerFactory.getLogger(CheatTaskService.class);
    public static final String ENABLED_CHEAT_TASKS = "enabledCheatTasks";

    @Autowired
    private CheatTaskRepository repository;
    @Autowired
    private InstaOrderSettingsService settingsService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private CheatTaskProviderService providerService;
    @Autowired
    private LockService lockService;

    private final Map<String, CheatTaskProvider> providerMap = new HashMap<>();

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        schedulerService.register(UpdateCheatServiceTask.class, "Обновление информации о сервисах");
    }

    public void register(CheatTaskProvider cheatTaskProvider) {
        String name = cheatTaskProvider.getName();
        providerMap.put(name, cheatTaskProvider);
        CheatTaskProviderBean providerBean = providerService.repository().findByName(name);
        if (providerBean == null) {
            providerBean = new CheatTaskProviderBean();
            providerBean.setName(name);
            providerBean.setActive(true);
            providerBean.setApiKey(cheatTaskProvider.getApiKey());
            providerBean.setApiUrl(cheatTaskProvider.getApiUrl());
            providerService.save(providerBean);
        }
    }

    private void internalUpdateTasks() {
        for (CheatTaskProvider cheatTaskProvider : providerMap.values()) {
            boolean active = cheatTaskProvider.isActive();
            List<CheatTaskBean> newBeans = active ? getActualTasks(cheatTaskProvider) : new ArrayList<>();
            if (active && newBeans.isEmpty()) {
                continue;
            }
            //Выключаем старые
            Map<Integer, CheatTaskBean> newBeanMap = CollectionHelper.createIdMap(newBeans, CheatTaskBean::getServiceNumber);
            List<CheatTaskBean> oldBeans = repository.findByProviderName(cheatTaskProvider.getName());
            oldBeans.stream().filter(oldBean -> !newBeanMap.containsKey(oldBean.getServiceNumber())).forEach(this::disable);
            //Уже ранее зарегистрированные сервисы
            Map<Integer, CheatTaskBean> oldBeanMap = CollectionHelper.createIdMap(oldBeans, CheatTaskBean::getServiceNumber);
            //Добавляем/изменяем услуги
            for (CheatTaskBean newBean : newBeans) {
                int serviceNumber = newBean.getServiceNumber();
                if (oldBeanMap.containsKey(serviceNumber)) {
                    CheatTaskBean oldBean = oldBeanMap.get(serviceNumber);
                    //В старый переносим новые значения
                    oldBean.setName(newBean.getName());
                    oldBean.setDescription(newBean.getDescription());
                    oldBean.setCostFor1000(newBean.getCostFor1000());
                    oldBean.setMin(newBean.getMin());
                    oldBean.setMax(newBean.getMax());
                    oldBean.setRefillDays(newBean.getRefillDays());
                    if (oldBean.isNotifyOnActive()) {
                        notificationService.sendMessage("Сервис " + oldBean + " вновь активен");
                        oldBean.setNotifyOnActive(false);
                    }
                    newBean = oldBean;
                } else {
                    newBean.setActive(true);
                }
                newBean.setDisabled(false);
                save(newBean);
            }
        }
        settingsService.clearCache();
    }

    private List<CheatTaskBean> getActualTasks(CheatTaskProvider cheatTaskProvider) {
        if (cheatTaskProvider.isActive()) {
            try {
                return cheatTaskProvider.getActualTasks();
            } catch (ServiceTemporaryException e) {
                e.log(log, "Error on get actual tasks for cheatTaskProvider:" + cheatTaskProvider);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Обновляем информацию о сервисах поставщиков
     */
    public void updateTasks() {
        lockService.runIfNoLock(CheatTaskService.class.getName(), this::internalUpdateTasks);
    }

    /**
     * @return список включенных задач отсортированных по стоимости
     */
    @Cacheable(value = ENABLED_CHEAT_TASKS)
    public List<CheatTaskBean> getEnabledSortedTasks(InstaOrderTaskType taskType) {
        List<CheatTaskBean> tasks = repository().findByTaskTypeAndDisabled(taskType, false);
        tasks.sort(((Comparator<CheatTaskBean>) (o1, o2) -> Boolean.compare(o2.isFavorite(), o1.isFavorite()))
                .thenComparingDouble(CheatTaskBean::getCostFor1000));
        return tasks;
    }

    @Override
    protected void onChange(CheatTaskBean bean) {
        evictAll(ENABLED_CHEAT_TASKS);
    }

    @Override
    public void delete(CheatTaskBean bean) {
        super.delete(bean);
    }

    private CheatTaskProvider taskProvider(CheatTaskBean cheatTask) {
        return providerMap.get(cheatTask.getProviderName());
    }

    private Optional<CheatTaskProvider> taskProvider(IOTaskBean taskBean) {
        if (taskBean.getCheatTask() == null) {
            return Optional.empty();
        }
        return Optional.of(taskProvider(taskBean.getCheatTask()));
    }

    public void abort(IOTaskBean taskBean) throws ServiceTemporaryException {
        withActiveTaskProvider(taskBean, taskProvider -> taskProvider.abort(taskBean));
    }

    public void update(IOTaskBean taskBean) throws ServiceTemporaryException {
        withActiveTaskProvider(taskBean, taskProvider -> taskProvider.update(taskBean));
    }

    public void runFollowers(IOFollowersTaskBean taskBean) throws ServiceTemporaryException {
        withActiveTaskProvider(taskBean, taskProvider -> taskProvider.runFollowers(taskBean));
    }

    public void runLikes(IOLikesTaskBean taskBean) throws ServiceTemporaryException {
        withActiveTaskProvider(taskBean, taskProvider -> taskProvider.runLikes(taskBean));
    }

    public void runComments(IOCommentsTaskBean taskBean) throws ServiceTemporaryException {
        withActiveTaskProvider(taskBean, taskProvider -> taskProvider.runComments(taskBean));
    }

    public void disable(CheatTaskBean cheatTask) {
        if (settingsService.notifyDisabledTask(cheatTask)) {
            notificationService.sendMessage("Заблокирован сервис:" + cheatTask);
        }
        cheatTask.setDisabled(true);
        save(cheatTask);
    }

    public void runViews(IOViewsTaskBean taskBean) throws ServiceTemporaryException {
        withActiveTaskProvider(taskBean, taskProvider -> taskProvider.runViews(taskBean));
    }

    private void withActiveTaskProvider(IOTaskBean taskBean, ConsumerWithServiceException<CheatTaskProvider> consumer) throws ServiceTemporaryException {
        Optional<CheatTaskProvider> cheatTaskProvider = taskProvider(taskBean);
        if (cheatTaskProvider.isPresent() && cheatTaskProvider.get().isActive()) {
            consumer.accept(cheatTaskProvider.get());
        }
    }

    public void refill(IOTaskBean taskBean) throws ServiceTemporaryException {
        withActiveTaskProvider(taskBean, taskProvider -> taskProvider.refill(taskBean));
    }
}
