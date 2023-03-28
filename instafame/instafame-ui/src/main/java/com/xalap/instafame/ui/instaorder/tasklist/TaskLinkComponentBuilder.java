/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.router.RouterLink;
import com.xalap.instafame.service.instaorder.task.AbstractMediaTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.ui.instaorder.cheattask.CheatTaskFrame;
import com.xalap.vaadin.custom.component.fluent.Label;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;

/**
 * @author Usov Andrey
 * @since 2020-10-17
 */
public class TaskLinkComponentBuilder {

    public static Component taskLink(IOTaskBean taskBean) {
        if (taskBean.getCheatTask() == null) {
            return new Label(taskBean.getTaskType().getCaption());
        }
        CustomVerticalLayout layout = new CustomVerticalLayout();
        layout.add(new RouterLink(taskBean.getCheatTask().caption(), CheatTaskFrame.class, taskBean.getCheatTask().getId()));
        layout.add(new Label("Цена: " + taskBean.getCheatTask().price()).bold());
        if (taskBean.getTaskType() != InstaOrderTaskType.followers) {
            AbstractMediaTaskBean mediaTaskBean = (AbstractMediaTaskBean) taskBean;
            Anchor anchor = new Anchor(mediaTaskBean.url(), "Публикация");
            anchor.setTarget("_blank");
            layout.add(anchor);
        }
        return layout;
    }
}
