/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 18/05/2019
 */
public class IOTaskRunner {

    private final CheatTaskService cheatTaskService;
    private final IOTaskProvider provider;

    public IOTaskRunner(CheatTaskService cheatTaskService, IOTaskProvider provider) {
        this.cheatTaskService = cheatTaskService;
        this.provider = provider;
    }

    /**
     * @return Задачи, которые нужно выполнить на текущий момент
     */
    public List<IOTaskBean> getTasksForRun(IOBean orderBean) {
        List<IOTaskBean> tasksForRun = new ArrayList<>();
        for (InstaOrderTaskType instaOrderTaskType : InstaOrderTaskType.values()) {
            instaOrderTaskType.addRunTasks(this, tasksForRun, orderBean);
        }
        return tasksForRun;
    }

    public void runTasks(List<IOTaskBean> tasksForRun, IOBean orderBean, InstaOrderTaskType taskType) {
        runTasks(tasksForRun, orderBean, taskType, () -> {
            return provider.getTasks(orderBean, taskType, InstaOrderTaskStatus.InProgress);
        });
    }

    private <T extends IOTaskBean> void runTasks(List<IOTaskBean> tasksForRun, IOBean orderBean, InstaOrderTaskType taskType,
                                                 Supplier<List<T>> inProgressTaskSupplier) {
        List<T> tasks = provider.getTasks(orderBean, taskType, InstaOrderTaskStatus.Created);
        if (!tasks.isEmpty()) {
            List<T> inProgressTasks = inProgressTaskSupplier.get();
            //Можно с каждого сервиса отправить только одну задачу
            Function<T, String> alreadyFunction =
                    taskBean -> taskType.getRunKey(cheatTaskService, inProgressTasks, taskBean);
            runTasks(tasks, inProgressTasks, alreadyFunction, tasksForRun::add);
        }
    }

    /**
     * Запускаем только те задачи, которые удовлетворяют условию
     */
    private <T extends IOTaskBean> void runTasks(List<T> tasks, List<T> inProgressTasks,
                                                 Function<T, String> alreadyFunction, Consumer<T> runner) {
        Set<String> alreadyProcessed = inProgressTasks.stream().map(alreadyFunction).collect(Collectors.toSet());

        tasks.stream().filter(task -> task.getCheatTask() != null && !alreadyProcessed.contains(alreadyFunction.apply(task))).forEach(task -> {
            if (new Date().after(task.getCreateTime())) {
                alreadyProcessed.add(alreadyFunction.apply(task));
                runner.accept(task);
            }
        });
    }

    public void addRunFollowersTasks(List<IOTaskBean> tasksForRun, IOBean orderBean) {
        //Подписчики
        runTasks(tasksForRun, orderBean, InstaOrderTaskType.followers, () -> {
            return provider.getTasks(orderBean.getInstagram(),
                    InstaOrderTaskType.followers, InstaOrderTaskStatus.InProgress);
        });
    }
}
