/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.vaadin.flow.function.SerializableSupplier;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.task.IOCommentsTaskBean;
import com.xalap.instafame.service.instaorder.task.IOLikesTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import com.xalap.instafame.service.instaorder.task.provider.MemoryOrderIOTaskProvider;
import com.xalap.instagram.service.media.MediaBean;
import com.xalap.vaadin.custom.data.DataHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Хранит информацию о задачах заказа и умеет ее обновлять
 * @author Усов Андрей
 * @since 09/05/2019
 */
public class IOTaskListHolder extends DataHolder<IOBean> {

    private final SerializableSupplier<MemoryOrderIOTaskProvider> taskProvider;
    private List<IOTaskBean> taskBeanList;
    private Map<MediaBean, List<IOCommentsTaskBean>> commentsMap;
    private IOStats stats;

    public IOTaskListHolder(SerializableSupplier<MemoryOrderIOTaskProvider> taskProvider) {
        this.taskProvider = taskProvider;
    }

    public void addListener(Runnable runnable) {
        addListener(value1 -> runnable.run());
    }

    @Override
    protected void doRefresh() {
        MemoryOrderIOTaskProvider memoryOrderIOTaskProvider = getTaskProvider();
        taskBeanList = memoryOrderIOTaskProvider.getTasks(value);
        taskBeanList.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        commentsMap = getCommentsMap();
        stats = new IOStats(value, getTaskBeanList());
    }

    private MemoryOrderIOTaskProvider getTaskProvider() {
        MemoryOrderIOTaskProvider memoryOrderIOTaskProvider = taskProvider.get();
        memoryOrderIOTaskProvider.setTaskList(value);
        return memoryOrderIOTaskProvider;
    }

    public IOStats getStats() {
        return stats;
    }

    public List<IOTaskBean> getTaskBeanList() {
        return taskBeanList;
    }

    public List<IOCommentsTaskBean> getCommentsList() {
        List<IOCommentsTaskBean> result = new ArrayList<>();
        getTaskBeanList().stream().filter(IOTaskBean::isComments).forEach(ioTaskBean -> {
            IOCommentsTaskBean commentsTaskBean = ioTaskBean.cast();
            result.add(commentsTaskBean);
        });
        return result;
    }

    public List<IOLikesTaskBean> getLikesList() {
        List<IOLikesTaskBean> result = new ArrayList<>();
        getTaskBeanList().stream().filter(IOTaskBean::isLikes).forEach(ioTaskBean -> {
            IOLikesTaskBean taskBean = ioTaskBean.cast();
            result.add(taskBean);
        });
        return result;
    }

    private Map<MediaBean, List<IOCommentsTaskBean>> getCommentsMap() {
        Map<MediaBean, List<IOCommentsTaskBean>> result = new HashMap<>();
        getTaskBeanList().stream().filter(IOTaskBean::isComments).forEach(ioTaskBean -> {
            IOCommentsTaskBean commentsTaskBean = ioTaskBean.cast();
            if (commentsTaskBean.getMedia() != null) {
                result.computeIfAbsent(commentsTaskBean.getMedia(), mediaBean -> new ArrayList<>()).add(commentsTaskBean);
            }
        });
        return result;
    }

    public List<IOCommentsTaskBean> getComments(MediaBean mediaBean) {
        return commentsMap.get(mediaBean);
    }

    public void withRefresh(BiConsumer<IOBean, IOTaskProvider> consumer) {
        withRefresh(() -> consumer.accept(value, getTaskProvider())).run();
    }
}
