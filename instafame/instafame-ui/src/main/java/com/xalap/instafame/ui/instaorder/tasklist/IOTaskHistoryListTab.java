/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.vaadin.flow.component.html.Label;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryBean;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryService;
import com.xalap.vaadin.custom.tab.ListTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Collections;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 22/04/2019
 */
public class IOTaskHistoryListTab extends ListTab<IOTaskHistoryBean, IOTaskBean> {

    public IOTaskHistoryListTab(ServiceRef<IOTaskHistoryService> service) {
        Label timeLabel = new Label("Нету истории");
        add(timeLabel);
        gridPanel.columns().add(IOTaskHistoryBean.CREATE_TIME, IOTaskHistoryBean.PERCENT);
        gridPanel.dataSource().setMemoryDataProvider(() -> {
            List<IOTaskHistoryBean> historyBeanList = service.get().repository().findByTask(getParentBean());
            Collections.sort(historyBeanList, (o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
            return historyBeanList;
        });

        parentBeanHolder.addListener((bean) -> timeLabel.setText("Время:" + bean.duration()));
    }

}
