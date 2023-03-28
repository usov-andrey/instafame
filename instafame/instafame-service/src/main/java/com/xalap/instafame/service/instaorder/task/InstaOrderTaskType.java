/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.settings.InstaOrderSettingsService;
import com.xalap.instafame.service.instaorder.task.runner.IOTaskRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 13/01/2019
 */
public enum InstaOrderTaskType {

    followers("Подписчики") {
        @Override
        public void run(IOTaskBean taskBean, CheatTaskService service) throws ServiceTemporaryException {
            service.runFollowers((IOFollowersTaskBean) taskBean);
        }

        @Override
        public int orderCount(IOBean bean) {
            return bean.getFollowersCount();
        }

        @Override
        public <T extends IOTaskBean> String getRunKey(CheatTaskService service, List<T> inProgressTasks, T taskBean) {
            //Нельзя заказывать подписчиков на один аккаунт с одного сервиса несколько одновременно
            return taskBean.getCheatTask().getProviderName();
        }

        @Override
        public Optional<CheatTaskBean> defaultTask(InstaOrderSettingsService settingsService) {
            return settingsService.getPremiumFollowersTask();
        }

        @Override
        public void addRunTasks(IOTaskRunner ioTaskRunner, List<IOTaskBean> tasksForRun, IOBean orderBean) {
            ioTaskRunner.addRunFollowersTasks(tasksForRun, orderBean);
        }
    },
    likes("Лайки") {
        @Override
        public void run(IOTaskBean taskBean, CheatTaskService service) throws ServiceTemporaryException {
            service.runLikes((IOLikesTaskBean) taskBean);
        }

        @Override
        public int orderCount(IOBean bean) {
            return bean.getLikesCount();
        }

        @Override
        public <T extends IOTaskBean> String getRunKey(CheatTaskService service, List<T> inProgressTasks, T taskBean) {
            //Нельзя заказывать лайки на одну публикацию с одного сервиса несколько одновременно
            IOLikesTaskBean likesTaskBean = (IOLikesTaskBean) taskBean;
            return likesTaskBean.getCode() + "_" +
                    taskBean.getCheatTask().getProviderName();
        }

        @Override
        public Optional<CheatTaskBean> defaultTask(InstaOrderSettingsService settingsService) {
            return settingsService.getLikesTask();
        }
    },
    comments("Комментарии") {
        @Override
        public void run(IOTaskBean taskBean, CheatTaskService service) throws ServiceTemporaryException {
            service.runComments((IOCommentsTaskBean) taskBean);
        }

        @Override
        public int orderCount(IOBean bean) {
            return bean.getCommentsCount();
        }

        @Override
        public <T extends IOTaskBean> String getRunKey(CheatTaskService service, List<T> inProgressTasks, T taskBean) {
            IOCommentsTaskBean commentsTaskBean = (IOCommentsTaskBean) taskBean;
            if (!taskBean.getCheatTask().isCustomComment()) {
                //Нельзя заказывать случайные коммментарии на одну публикацию с одного сервиса несколько одновременно
                return commentsTaskBean.getCode() + "_" + taskBean.getCheatTask().getProviderName();
            }
            //Для комментариев, где текст задается вручную немного другая стратегия
            //Для того, чтобы один и тот же аккаунт не оставлять комментерии ко всем публикациям

            boolean isNewTask = !inProgressTasks.contains(taskBean);
            if (isNewTask) {//Мы не можем отправить больше чем один комментарий за раз
                return taskBean.getCheatTask().taskName();
            }
            //Если в inProgress уже есть комментарий и он был отправлен ранее чем 12 часов назад, то можно отправить еще один комментарий
            if (taskBean.getSendTime() != null && DateHelper.minutesBetweenDates(taskBean.getSendTime(), new Date()) > 60 * 12) {
                return "already";
            }
            return taskBean.getCheatTask().taskName();
        }

        @Override
        public Optional<CheatTaskBean> defaultTask(InstaOrderSettingsService settingsService) {
            return settingsService.getCustomCommentsTask();
        }
    },
    views("Просмотры видео") {
        @Override
        public void run(IOTaskBean taskBean, CheatTaskService service) throws ServiceTemporaryException {
            service.runViews((IOViewsTaskBean) taskBean);
        }

        @Override
        public int orderCount(IOBean bean) {
            return bean.getViewsCount();
        }

        @Override
        public <T extends IOTaskBean> String getRunKey(CheatTaskService service, List<T> inProgressTasks, T taskBean) {
            //Нельзя заказывать лайки на одну публикацию с одного сервиса несколько одновременно
            IOViewsTaskBean viewsTaskBean = (IOViewsTaskBean) taskBean;
            return viewsTaskBean.getCode() + "_" + taskBean.getCheatTask().getProviderName();
        }

        @Override
        public Optional<CheatTaskBean> defaultTask(InstaOrderSettingsService settingsService) {
            return settingsService.getViewsTask();
        }
    };

    private final String caption;

    InstaOrderTaskType(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    abstract public void run(IOTaskBean taskBean, CheatTaskService service) throws ServiceTemporaryException;

    abstract public int orderCount(IOBean bean);

    public abstract <T extends IOTaskBean> String getRunKey(CheatTaskService service, List<T> inProgressTasks, T taskBean);

    abstract public Optional<CheatTaskBean> defaultTask(InstaOrderSettingsService settingsService);

    public void addRunTasks(IOTaskRunner ioTaskRunner, List<IOTaskBean> tasksForRun, IOBean orderBean) {
        ioTaskRunner.runTasks(tasksForRun, orderBean, this);
    }
}
