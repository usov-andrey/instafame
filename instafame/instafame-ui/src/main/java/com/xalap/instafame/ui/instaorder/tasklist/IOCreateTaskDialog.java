/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.xalap.framework.utils.MinMax;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.task.CreateCommentTasks;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.IOTaskSpeed;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.ui.instaorder.components.ExtServiceComboBox;
import com.xalap.vaadin.custom.component.EnumComboBox;
import com.xalap.vaadin.custom.component.IntegerField;
import com.xalap.vaadin.custom.crud.BeanFormDialog;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 18/04/2019
 */
public class IOCreateTaskDialog extends BeanFormDialog<CreateTaskBean> {

    private final ServiceRef<IOTaskService> taskService;

    public IOCreateTaskDialog(ServiceRef<IOTaskService> taskService) {
        super(CreateTaskBean.class);
        this.taskService = taskService;
    }

    @Override
    protected void save(CreateTaskBean bean) {
        if (bean.getTaskType() == InstaOrderTaskType.followers) {
            taskService.get().orderFollowers(bean.getOrderBean(), bean.getCheatTaskBean(), bean.getQuantity(),
                    bean.getSpeed() != null ? bean.getSpeed().getSpeed() : null, new Date());
        } else if (bean.getTaskType() == InstaOrderTaskType.likes) {
            taskService.get().orderForMediasByUrls(bean.getUrls(), bean.getQuantity(), (mediaCode, quantity) ->
                    taskService.get().orderLikes(mediaCode, null, bean.getOrderBean(), bean.getCheatTaskBean(),
                            quantity, new MinMax(0, 10),
                            bean.getSpeed() != null ? bean.getSpeed().getSpeed() : null, new Date()));
        } else if (bean.getTaskType() == InstaOrderTaskType.comments) {
            orderComments(bean);
        } else if (bean.getTaskType() == InstaOrderTaskType.views) {
            taskService.get().orderForMediasByUrls(bean.getUrls(), bean.getQuantity(), (mediaCode, quantity) ->
                    taskService.get().orderViews(mediaCode, null, bean.getOrderBean(), bean.getCheatTaskBean(),
                            quantity, new MinMax(0, 50),
                            bean.getSpeed() != null ? bean.getSpeed().getSpeed() : null, new Date())
            );
        }
    }

    private void orderComments(CreateTaskBean bean) {
        List<String> mediaCodes = taskService.get().getMediaCodes(bean.getUrls());
        if (!mediaCodes.isEmpty()) {
            List<String> comments = new CreateCommentTasks().getTextByLines(bean.getCommentText());
            int commentsByUrl = comments.size() / mediaCodes.size();
            taskService.get().orderForMediasByUrls(mediaCodes, bean.getQuantity(), (mediaCode, quantity) -> {
                //Разделяем комментарии по публикациям
                int index = mediaCodes.indexOf(mediaCode);
                String comment = getComments(comments, index, commentsByUrl);
                taskService.get().orderComments(mediaCode, null, bean.getOrderBean(), bean.getCheatTaskBean(),
                        quantity, comment,
                        bean.getSpeed() != null ? bean.getSpeed().getSpeed() : null, new Date());
            });
        }
    }

    private String getComments(List<String> comments, int index, int commentsByUrl) {
        if (comments.isEmpty()) {
            return null;
        }
        int start = index * commentsByUrl;
        int end = start + commentsByUrl;
        if (end > comments.size()) {
            end = comments.size();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            if (sb.length() > 0) {
                sb.append(StringHelper.END_LINE);
            }
            sb.append(comments.get(i));
        }
        return sb.toString();
    }

    @Override
    protected void setupFormLayout(FormLayout formLayout) {
        //Размещаем в один столбец
    }

    @Override
    protected void addFields() {
        //Формируем поля в ручном режиме
        EnumComboBox<InstaOrderTaskType> taskType = new EnumComboBox<>("Тип услуги", InstaOrderTaskType.class);
        addRequiredField(taskType).bind(CreateTaskBean::getTaskType, CreateTaskBean::setTaskType);

        ComboBox<CheatTaskBean> extService = new ExtServiceComboBox();
        extService.setLabel("Услуга");
        Binder.BindingBuilder<CreateTaskBean, CheatTaskBean> createTaskBeanCheatTaskBindingBuilder = addField(extService);

        IntegerField quantity = new IntegerField("Количество");
        addRequiredField(quantity).bind(CreateTaskBean::getQuantity, CreateTaskBean::setQuantity);

        TextArea mediaUrls = new TextArea("Список ссылок на публикации");
        mediaUrls.getStyle().set("minHeight", "150px");
        addField(mediaUrls).bind(CreateTaskBean::getUrls, CreateTaskBean::setUrls);
        mediaUrls.setVisible(false);

        ComboBox<IOTaskSpeed> speed = new ComboBox<>("Скорость");
        addField(speed).bind(CreateTaskBean::getSpeed, CreateTaskBean::setSpeed);
        speed.setItems(IOTaskSpeed.values());
        speed.setItemLabelGenerator(item -> Integer.toString(item.getSpeed()));
        speed.setVisible(false);
        speed.setWidthFull();

        TextArea commentText = new TextArea("Текст комментария");
        commentText.setMaxHeight("300px");
        addField(commentText).bind(CreateTaskBean::getCommentText, CreateTaskBean::setCommentText);
        commentText.setVisible(false);

        taskType.addValueChangeListener(event -> {
            extService.setItems(event.getValue() != null ?
                    taskService.get().getCheatTaskService().getEnabledSortedTasks(event.getValue()) : Collections.emptyList());
            mediaUrls.setVisible(event.getValue() != InstaOrderTaskType.followers);
            commentText.setVisible(event.getValue() == InstaOrderTaskType.comments);
        });

        //При изменении услуги проставляем ограничения
        createTaskBeanCheatTaskBindingBuilder.bind(
                CreateTaskBean::getCheatTaskBean,
                (createTaskBean, cheatTask) -> {
                    if (cheatTask != null) {
                        createTaskBean.setCheatTaskBean(cheatTask);
                        speed.setVisible(cheatTask.isSpeedSupport());
                        quantity.setMin(cheatTask.getMin());
                        quantity.setMax(cheatTask.getMax());
                    } else {
                        speed.setVisible(false);
                    }
                });

    }


}
