/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.integration.lock.LockService;
import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.spring.SpringProfiles;
import com.xalap.framework.task.async.AsyncTaskRunner;
import com.xalap.framework.utils.MinMax;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.cheat.exception.*;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.settings.InstaOrderSettingsService;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import com.xalap.instafame.service.instaorder.task.provider.MemoryIOTaskProvider;
import com.xalap.instafame.service.instaorder.task.provider.MemoryOrderIOTaskProvider;
import com.xalap.instafame.service.instaorder.task.runner.IOTaskRunner;
import com.xalap.instafame.service.instaorder.task.runner.LikeRunner;
import com.xalap.instagram.api.media.MediaType;
import com.xalap.instagram.service.media.MediaBean;
import com.xalap.instagram.service.media.MediaService;
import com.xalap.instagram.service.user.InstagramUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Service
public class IOTaskService extends CrudService<IOTaskBean, IOTaskRepository, Integer> {

    private static final Logger log = LoggerFactory.getLogger(IOTaskService.class);

    private final InstagramUserService userService;
    private final MediaService mediaService;
    private final NotificationService notificationService;
    private final CheatTaskService cheatTaskService;
    private final InstaOrderSettingsService settings;
    private final IOTaskHistoryService ioTaskHistoryService;
    private final LockService lockService;
    private final SpringProfiles springProfiles;
    private final ExecutorService executorService;

    public IOTaskService(InstagramUserService userService, MediaService mediaService, NotificationService notificationService, CheatTaskService cheatTaskService, InstaOrderSettingsService settings, IOTaskHistoryService ioTaskHistoryService, LockService lockService, SpringProfiles springProfiles, ExecutorService executorService) {
        super();
        this.userService = userService;
        this.mediaService = mediaService;
        this.notificationService = notificationService;
        this.cheatTaskService = cheatTaskService;
        this.settings = settings;
        this.ioTaskHistoryService = ioTaskHistoryService;
        this.lockService = lockService;
        this.springProfiles = springProfiles;
        this.executorService = executorService;
    }

    /**
     * Отправляем заказ во внешнюю систему
     */
    public void runTask(final IOTaskBean taskBean) {
        lockService.runIfNoLock(taskBean.getId().toString(), () -> internalRunTask(taskBean));
    }

    private void internalRunTask(IOTaskBean taskBean) {
        if (springProfiles.isDevelopment()) {
            throw new IllegalStateException("In development can't runTask");
        }
        if (taskBean.getStatus() != InstaOrderTaskStatus.Created) {
            //Уже была выполнена
            return;
        }
        if (taskBean.getCheatTask() == null) {
            //Значит ручная задача для статистики
            return;
        }

        try {
            //Выполняем
            taskBean.getTaskType().run(taskBean, cheatTaskService);
            if (taskBean.getExtOrderId() != null) {
                //Сохраняем информацию
                taskBean.setSendTime(new Date());
                taskBean.setStatus(InstaOrderTaskStatus.InProgress);
                taskBean.setNotifyOnDelay(true);
            } else {//Возможно сервис сейчас не доступен
                taskBean.setStatus(InstaOrderTaskStatus.ManualCheck);
            }

            log.debug("Save Task Bean:" + taskBean);
            save(taskBean);
            //Обрабатываем большое количество ошибок
        } catch (AlreadyOrderException e) {
            log.warn("Order already have for taskBean:" + taskBean);
            cancelTask(taskBean);
        } catch (NoBalanceException e) {
            notificationService.sendMessage(e.getMessage());
            taskBean.setStatus(InstaOrderTaskStatus.ManualCheck);
            save(taskBean);
        } catch (ServiceDisabledException e) {
            cheatTaskService.disable(taskBean.getCheatTask());
            taskBean.setStatus(InstaOrderTaskStatus.Canceled);
            save(taskBean);
        } catch (MediaDeletedException e) {
            log.warn("Media deleted in task:" + taskBean, e);
            cancelTask(taskBean);
        } catch (AccountIsPrivateException e) {
            log.warn("Account is private :" + taskBean.getOrder().getUser().getUserName(), e);
            userService.asyncUpdateUserWithLastMedia(taskBean.getOrder().getUser().getUserName());
            cancelTask(taskBean);
        } catch (Exception e) {
            log.error("Error on run task:" + taskBean, e);
            taskBean.setStatus(InstaOrderTaskStatus.ManualCheck);
            save(taskBean);
        }
    }

    /**
     * Обновляем статус заказа во внешней системе
     */
    public void updateTask(IOTaskBean taskBean) {
        //Обновляем информацию о заказа в системе
        if (StringHelper.isNotEmpty(taskBean.getExtOrderId())) {
            try {
                cheatTaskService.update(taskBean);
                save(taskBean);
            } catch (ServiceTemporaryException e) {
                log.warn("Error on updateTask:" + taskBean, e);
            }
        }
    }

    public void orderFollowers(IOBean orderBean, CheatTaskBean cheatTaskBean, Integer quantity, Integer speed, Date time) {
        IOTaskBean taskBean = new IOFollowersTaskBean();
        taskBean.setTaskType(InstaOrderTaskType.followers);
        if (speed != null) {
            taskBean.setTimeUnitSpeed(TimeUnit.HOURS);
        }
        saveNewTask(taskBean, orderBean, cheatTaskBean, quantity, speed, time);
    }

    /**
     * Заказать услугу на список ссылок публикаций
     */
    public void orderForMediasByUrls(String urls, Integer quantity, BiConsumer<String, Integer> consumer) {
        List<String> selectedMedia = getMediaCodes(urls);
        orderForMediasByUrls(selectedMedia, quantity, consumer);
    }

    /**
     * Заказать услугу на список ссылок публикаций
     */
    public void orderForMediasByUrls(List<String> selectedMedia, Integer quantity, BiConsumer<String, Integer> consumer) {
        int mediaCount = selectedMedia.size();
        int forOneMedia = quantity / mediaCount;
        for (String media : selectedMedia) {
            consumer.accept(media, forOneMedia);
        }
    }

    public List<String> getMediaCodes(String urls) {
        List<String> result = new ArrayList<>();
        for (String url : urls.split(StringHelper.END_LINE)) {
            //Формат: https://www.instagram.com/p/%s/
            //или формат: https://www.instagram.com/reel/%s
            //или формат: https://www.instagram.com/tv/%s
            String code = StringHelper.getStringAfter(url, "/p/");
            if (code.isEmpty()) {
                code = StringHelper.getStringAfter(url, "/reel/");
            }
            if (code.isEmpty()) {
                code = StringHelper.getStringAfter(url, "/tv/");
            }
            code = code.replace("/", "");
            result.add(code);
        }
        return result;
    }

    public void orderForLastMedias(IOBean orderBean, Integer lastMediaCount, Consumer<MediaBean> mediaBeanConsumer) {
        List<MediaBean> userLastMedia = mediaService.getUserLastMediaList(orderBean.getUser(), lastMediaCount);
        if (lastMediaCount > userLastMedia.size()) {
            //Пробуем загрузить последние lastMediaCount публикаций
            try {
                userService.updateUserWithAllMedia(orderBean.getUser().getUserName());
            } catch (ServiceTemporaryException e) {
                log.error("Error on update user medias:" + orderBean.getUser(), e);
                throw new IllegalStateException("Ошибка при обновлении публикаций");
            }
            userLastMedia = mediaService.getUserLastMediaList(orderBean.getUser(), lastMediaCount);
            if (lastMediaCount > userLastMedia.size()) {
                throw new NotLoadedMediaException("Количество найденных публикаций(" + userLastMedia.size() + ") меньше, чем " + lastMediaCount);
            }
        }
        for (MediaBean mediaBean : userLastMedia) {
            mediaBeanConsumer.accept(mediaBean);
        }
    }

    public void orderViewsForLastMedias(IOBean orderBean, Integer lastMediaCount, Consumer<MediaBean> mediaBeanConsumer) {
        List<MediaBean> userLastMedia = mediaService.getUserLastMediaList(orderBean.getUser(), lastMediaCount);
        Stream<MediaBean> mediaBeanStream = userLastMedia.stream().filter(mediaBean -> mediaBean.getType() == MediaType.video).limit(lastMediaCount);
        if (mediaBeanStream.count() < lastMediaCount) {
            //Пробуем загрузить последние lastMediaCount публикаций
            try {
                userService.updateUserWithAllMedia(orderBean.getUser().getUserName());
            } catch (ServiceTemporaryException e) {
                e.log(log, "Error on update user medias:" + orderBean.getUser());
                throw new IllegalStateException("Ошибка при обновлении публикаций");
            }
            userLastMedia = mediaService.getUserLastMediaList(orderBean.getUser(), lastMediaCount);
            mediaBeanStream = userLastMedia.stream().filter(mediaBean -> mediaBean.getType() == MediaType.video).limit(lastMediaCount);
            if (mediaBeanStream.count() < lastMediaCount) {
                throw new IllegalStateException("Found only " + mediaBeanStream.count() + " video in last 12 media");
            }
        }
        mediaBeanStream.forEach(mediaBeanConsumer);
    }

    /**
     * Просмотров заказываем в 2 раза больше чем лайков
     * Для того, чтобы использовать эту функцию нам нужно MediaBean, так как проверяем тип публикации
     */
    public void orderLikesWithViews(MediaBean mediaBean, IOBean orderBean, CheatTaskBean cheatTaskBean, int likes,
                                    MinMax addLikes, Integer speed) {
        Date time = new Date();
        Optional<CheatTaskBean> viewsTask = getSettings().getViewsTask();
        if (viewsTask.isPresent()) {
            //Если медиа - это видео, то заказываем еще и просмотры
            if (mediaBean.getType() == MediaType.video) {
                CheatTaskBean viewsTaskBean = viewsTask.get();
                int views = likes * 2;
                if (views < viewsTaskBean.getMin()) {
                    views = viewsTaskBean.getMin();
                }
                orderViews(mediaBean.getCode(), mediaBean, orderBean, viewsTaskBean,
                        views, new MinMax(0, 10), null, time);
            }
        }
        orderLikes(mediaBean.getCode(), mediaBean, orderBean, cheatTaskBean,
                likes, addLikes,
                speed, time);
    }

    public void orderLikes(MediaBean mediaBean, IOBean orderBean, CheatTaskBean cheatTaskBean, int likes, MinMax addLikes, Integer speed) {
        orderLikes(mediaBean.getCode(), mediaBean, orderBean, cheatTaskBean,
                likes, addLikes,
                speed, new Date());
    }

    public void orderLikes(String mediaCode, MediaBean mediaBean, IOBean orderBean, CheatTaskBean cheatTaskBean, int likes, MinMax addLikes, Integer speed, Date time) {
        IOLikesTaskBean taskBean = new IOLikesTaskBean();
        taskBean.setTaskType(InstaOrderTaskType.likes);
        taskBean.setMedia(mediaBean);
        taskBean.setCode(mediaCode);
        if (speed != null) {
            taskBean.setTimeUnitSpeed(TimeUnit.MINUTES);
        }
        saveNewTask(taskBean, orderBean, cheatTaskBean, likes + addLikes.random(), speed, time);
    }

    public void orderViews(String mediaCode, MediaBean mediaBean, IOBean orderBean, CheatTaskBean cheatTaskBean, int views, MinMax addViews, Integer speed, Date time) {
        IOViewsTaskBean taskBean = new IOViewsTaskBean();
        taskBean.setTaskType(InstaOrderTaskType.views);
        taskBean.setMedia(mediaBean);
        taskBean.setCode(mediaCode);
        if (speed != null) {
            taskBean.setTimeUnitSpeed(TimeUnit.MINUTES);
        }
        saveNewTask(taskBean, orderBean, cheatTaskBean, views + addViews.random(), speed, time);
    }

    public void orderComments(String mediaCode, MediaBean mediaBean, IOBean orderBean, CheatTaskBean cheatTaskBean, int quantity, String comment, Integer speed, Date time) {
        IOCommentsTaskBean taskBean = new IOCommentsTaskBean();
        taskBean.setTaskType(InstaOrderTaskType.comments);
        taskBean.setComments(comment);
        taskBean.setMedia(mediaBean);
        taskBean.setCode(mediaCode);
        if (speed != null) {
            taskBean.setTimeUnitSpeed(TimeUnit.MINUTES);
        }
        saveNewTask(taskBean, orderBean, cheatTaskBean, quantity, speed, time);
    }

    private void saveNewTask(IOTaskBean taskBean, IOBean orderBean, CheatTaskBean cheatTaskBean, Integer quantity, Integer speed, Date time) {
        taskBean.setOrder(orderBean);
        taskBean.setCreateTime(time);
        taskBean.setQuantity(quantity);
        taskBean.setCheatTask(cheatTaskBean);
        if (cheatTaskBean != null) {
            //Сразу считаем стоимость
            taskBean.setCharge(cheatTaskBean.getCostFor1000() * quantity / 1000);
        }
        taskBean.setOrder(orderBean);
        taskBean.setCountSpeed(speed);
        taskBean.setStatus(InstaOrderTaskStatus.Created);
        save(taskBean);
    }

    /**
     * Обновить статус по всех задачах
     */
    public void updateTasks(IOBean parentBean, IOTaskProvider taskProvider) {
        List<IOTaskBean> tasks = taskProvider.getTasks(parentBean, InstaOrderTaskStatus.InProgress, InstaOrderTaskStatus.Canceled,
                InstaOrderTaskStatus.Interrupted);
        if (!tasks.isEmpty()) {
            log.debug("Update tasks order:" + parentBean);
            //Пытаемся выполнить все это параллельно
            asyncTaskRunner().runAndWait(tasks, this::updateTask, taskBean -> "ioTask." + taskBean.caption());
        }
    }

    private AsyncTaskRunner asyncTaskRunner() {
        return new AsyncTaskRunner(executorService);
    }

    public void runTasks(IOBean orderBean, IOTaskProvider taskProvider) {
        log.debug("Run tasks order:" + orderBean);
        List<IOTaskBean> tasksForRun = getTasksForRun(orderBean, taskProvider);
        asyncTaskRunner().runAndWait(tasksForRun, this::runTask, taskBean -> "ioTask." + taskBean.caption());
    }

    public List<IOTaskBean> getTasksForRun(IOBean orderBean, IOTaskProvider taskProvider) {
        return new IOTaskRunner(cheatTaskService, taskProvider).getTasksForRun(orderBean);
    }

    /**
     * Отменить выполнение, т.е. даже не начинать выполнять
     */
    public void cancelTask(IOTaskBean taskBean) {
        taskBean.setStatus(InstaOrderTaskStatus.Canceled);
        taskBean.setCharge(0d);
        save(taskBean);
    }

    /**
     * Добавить автоматически задания на следующие 24 часа
     */
    public void addAutoTasks(IOBean orderBean, IOTaskProvider taskProvider) {
        if (orderBean.getInstagram() != null) {
            orderBean.getFollowersPackage().getRunner(this, taskProvider).run(orderBean);
        } else {
            log.warn("User is null for order:{}", orderBean);
        }

        LikeRunner likeRunner = new LikeRunner(this, taskProvider, notificationService);
        likeRunner.run(orderBean);
    }

    public void restore(IOBean orderBean, IOTaskProvider taskProvider) {
        orderBean.getFollowersPackage().getRunner(this, taskProvider).manualRestore(orderBean);
    }

    /**
     * Прервать выполнение после уже начала выполнения
     */
    public void abortTask(IOTaskBean taskBean) {
        try {
            cheatTaskService.abort(taskBean);
            cheatTaskService.update(taskBean);
        } catch (ServiceTemporaryException e) {
            log.error("Error on abort task:" + taskBean, e);
        }
        save(taskBean);
    }

    public MemoryIOTaskProvider memoryTaskProvider(Collection<IOBean> orderBeanList) {
        return new MemoryIOTaskProvider(getRepository().findByOrders(orderBeanList));
    }

    public MemoryIOTaskProvider memoryTaskProvider(IOBean orderBean) {
        return new MemoryOrderIOTaskProvider(getRepository(), orderBean);
    }

    public MemoryIOTaskProvider memoryTaskProviderAll() {
        return new MemoryIOTaskProvider(getAll());
    }

    @Override
    public IOTaskBean save(IOTaskBean bean) {
        if (bean.getStatus() == InstaOrderTaskStatus.Canceled) {
            bean.setCharge(0d);
        }
        if (bean.getRemains() != null && bean.getRemains() < 0) {
            bean.setRemains(null);
        }
        if (bean.getTimeUnitSpeed() == null) {
            bean.setTimeUnitSpeed(TimeUnit.MINUTES);
        }
        if (bean.getId() != null) {
            IOTaskBean oldBean = get(bean.getId());
            if (bean.getStatus() == InstaOrderTaskStatus.Completed &&
                    oldBean.getStatus() != InstaOrderTaskStatus.Completed ||
                    bean.getStatus() == InstaOrderTaskStatus.Canceled &&
                            oldBean.getStatus() != InstaOrderTaskStatus.Canceled ||
                    bean.getStatus() == InstaOrderTaskStatus.Interrupted &&
                            oldBean.getStatus() != InstaOrderTaskStatus.Interrupted) {
                bean.setCompleteTime(new Date());
            }
            //Если изменился прогресс, то добавляем историю
            int newPercent = new IOStats.Stat(bean.quantityMinusRemains(), bean.getQuantity()).currentPercent();
            int oldPercent = new IOStats.Stat(oldBean.quantityMinusRemains(), oldBean.getQuantity()).currentPercent();
            if (newPercent != oldPercent && newPercent != 0
                    && bean.getStatus() != InstaOrderTaskStatus.Canceled) {//Да заказов которые еще не начались выполняться точно не нужна история выполнения
                ioTaskHistoryService.addHistory(bean, newPercent);
            }
        }
        if (bean.getExtOrderId() != null && bean.getSendTime() == null) {
            bean.setSendTime(new Date());
        }
        return super.save(bean);
    }

    public InstaOrderSettingsService getSettings() {
        return settings;
    }

    public CheatTaskService getCheatTaskService() {
        return cheatTaskService;
    }

    /**
     * Запустить восстановление по задаче
     */
    public void refill(IOTaskBean ioTaskBean) {
        try {
            cheatTaskService.refill(ioTaskBean);
            ioTaskBean.setRefillTime(new Date());
            save(ioTaskBean);
        } catch (ServiceTemporaryException e) {
            log.error("Error on refill task:" + ioTaskBean, e);
        }
    }

    /**
     * @return Список публикаций сделанных полььзователем после времени заказа
     */
    public List<MediaBean> getNewMediaList(IOBean bean) {
        List<MediaBean> userMediaList = mediaService.getUserMediaList(bean.getUser());
        return userMediaList.stream().filter(mediaBean -> mediaBean.getCreateTime().after(bean.getCreateTime()))
                .collect(Collectors.toList());
    }
}
