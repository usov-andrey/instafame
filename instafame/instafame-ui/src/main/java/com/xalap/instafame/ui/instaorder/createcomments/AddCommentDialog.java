/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.createcomments;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.ironlist.IronList;
import com.vaadin.flow.component.textfield.TextArea;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.commenttemplate.CommentTemplateBean;
import com.xalap.instafame.service.commenttemplate.CommentTemplateService;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOCommentsTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.ui.instaorder.components.ExtServiceComboBox;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskListHolder;
import com.xalap.instagram.service.media.MediaBean;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 15/05/2019
 */
public class AddCommentDialog extends Dialog {//Dialog - это HorizontalLayout

    private final IOTaskListHolder tasksProvider;
    private final IronList<IOCommentsTaskBean> commentList;
    private MediaBean mediaBean;
    private IOBean parentBean;

    public AddCommentDialog(ServiceRef<IOTaskService> ioTaskService,
                            ServiceRef<CommentTemplateService> commentTemplateService, IOTaskListHolder tasksProvider) {
        this.tasksProvider = tasksProvider;
        commentList = new IronList<>();
        commentList.setRenderer(bean1 -> bean1.getStatus() + " - " + DateHelper.getDateTime(bean1.getCreateTime()) +
                " - " + bean1.getComments());
        add(commentList);

        //Сервис с комментариями
        List<CheatTaskBean> commentTasks = ioTaskService.get().getCheatTaskService().getEnabledSortedTasks(InstaOrderTaskType.comments);
        commentTasks = commentTasks.stream().filter(CheatTaskBean::isCustomComment).collect(Collectors.toList());
        ExtServiceComboBox commentServiceComboBox = new ExtServiceComboBox(commentTasks);
        //Текст комментария
        TextArea commentText = new TextArea("Текст комментария");
        //Добавить комментарий - кнопка
        Button button = new Button("Добавить комментарий", (ComponentEventListener<ClickEvent<Button>>) event -> {
            if (StringHelper.isNotEmpty(commentText.getValue())) {
                CheatTaskBean cheatTask = commentServiceComboBox.getValue();
                ioTaskService.get().orderComments(mediaBean.getCode(), mediaBean, parentBean, cheatTask,
                        cheatTask.getMin(), commentText.getValue(),
                        null, new Date());
                commentText.setValue("");
                tasksProvider.refresh();
            }

        });

        commentServiceComboBox.setLabel("Задача");
        commentServiceComboBox.setDefaultValue(() -> ioTaskService.get().getSettings().getCustomCommentsTask());
        commentServiceComboBox.addValueChangeListener(event -> {
            CheatTaskBean cheatTask = event.getValue();
            if (cheatTask != null) {
                button.setText(cheatTask.getMin() == 1 ? "Добавить комментарий" : "Добавить " +
                        cheatTask.getMin() + " комментариев");
            }
        });

        commentText.setWidth("500px");
        commentText.setHeight("300px");

        ComboBox<CommentTemplateBean> templateBeanComboBox = new ComboBox<>("Шаблон");
        templateBeanComboBox.setItems(commentTemplateService.get().getAllSorted());
        templateBeanComboBox.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                commentText.setValue(event.getValue().getComText());
                templateBeanComboBox.clear();
            }
        });
        templateBeanComboBox.setItemLabelGenerator(CommentTemplateBean::getComText);

        //Размещаем
        CustomVerticalLayout layout = new CustomVerticalLayout();
        layout.add(commentServiceComboBox);
        layout.add(templateBeanComboBox);

        layout.add(templateBeanComboBox);
        layout.add(commentText);
        layout.add(button);

        add(layout);

    }

    public void setMedia(MediaBean mediaBean, IOBean parentBean) {
        List<IOCommentsTaskBean> comments = tasksProvider.getComments(mediaBean);
        if (comments != null) {
            commentList.setItems(comments);
        }
        this.mediaBean = mediaBean;
        this.parentBean = parentBean;
    }

}
