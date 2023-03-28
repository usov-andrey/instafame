/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.createcomments;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.commenttemplate.CommentTemplateService;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.*;
import com.xalap.instafame.ui.instaorder.components.ExtServiceComboBox;
import com.xalap.instafame.ui.instaorder.createlikes.MediaListHolder;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskListHolder;
import com.xalap.instagram.service.media.MediaBean;
import com.xalap.instagram.ui.MediaComponent;
import com.xalap.vaadin.custom.component.ButtonWithLog;
import com.xalap.vaadin.custom.component.EnumCountList;
import com.xalap.vaadin.custom.component.IntegerField;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.vaadin.custom.grid.BeanWithIdGridPanel;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.valueprovider.IntegerValueProvider;
import com.xalap.vaadin.custom.tab.Tab;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
public class IOCreateCommentsTab extends Tab<IOBean> {

    private final ServiceRef<IOTaskService> ioTaskService;
    private final IOTaskListHolder taskListHolder;

    public IOCreateCommentsTab(ServiceRef<IOTaskService> ioTaskService, MediaListHolder mediaListHolder,
                               IOTaskListHolder tasksProvider,
                               ServiceRef<CommentTemplateService> commentTemplateService) {
        this.ioTaskService = ioTaskService;
        this.taskListHolder = tasksProvider;

        EnumCountList<InstaOrderTaskStatus, IOCommentsTaskBean> countList =
                new EnumCountList<>(InstaOrderTaskStatus.class, tasksProvider::getCommentsList, IOTaskBean::getStatus);
        tasksProvider.addListener(countList::redraw);
        add(countList);

        BeanWithIdGridPanel<MediaBean, Integer> gridPanel = new BeanWithIdGridPanel<>(MediaBean.class);
        add(createFastOrderPanel(gridPanel.grid()));
        gridPanel.grid().setSelectionMode(Grid.SelectionMode.MULTI);

        gridPanel.dataSource().setMemoryDataProvider(mediaListHolder::getValue);
        tasksProvider.addListener(gridPanel::refreshAll);
        add(gridPanel);

        GridColumns<MediaBean> columns = gridPanel.columns();
        columns.addDateTime("Время", MediaBean::getCreateTime);
        columns.addComponent("Медиа", MediaComponent::new).setWidth("200px");
        columns.addText("Описание", MediaBean::getCaption, "300px", "200px");
        columns.add("Лайков", new IntegerValueProvider<>(mediaBean -> mediaBean.getStats().getLikesCount()));
        columns.addLinkWithPopup("Комм", mediaBean -> mediaBean.getStats().getCommentsCount() + "",
                mediaBean -> {
            VerticalLayout layout = new VerticalLayout();
            layout.add(new Label("Функции получения комментариев больше нет"));
            return layout;
        });
        columns.addComponent("Задания", mediaBean -> {
            List<IOCommentsTaskBean> commentsTaskBeanList = tasksProvider.getComments(mediaBean);
            if (commentsTaskBeanList != null) {
                //Группируем по статусам
                EnumCountList<InstaOrderTaskStatus, IOCommentsTaskBean> countList1 = new EnumCountList<>(InstaOrderTaskStatus.class, () -> commentsTaskBeanList, IOTaskBean::getStatus);
                countList1.redraw();
                return countList1;
            }
            return new Label();
        });

        AddCommentDialog dialog = new AddCommentDialog(ioTaskService, commentTemplateService, tasksProvider);

        columns.actions((mediaBeanGridActions, mediaBean) -> {
            mediaBeanGridActions.add("Добавить", () -> {
                dialog.setMedia(mediaBean, getParentBean());
                dialog.open();
            });
        });
    }

    private Component createFastOrderPanel(Grid<MediaBean> grid) {
        FlexHorizontalLayout result = new FlexHorizontalLayout();
        List<CheatTaskBean> cheatTaskBeans = ioTaskService.get().getCheatTaskService().getEnabledSortedTasks(InstaOrderTaskType.comments);
        List<CheatTaskBean> notCustomComment = cheatTaskBeans.stream().filter(cheatTaskBean ->
                !cheatTaskBean.isCustomComment()).collect(Collectors.toList());
        ExtServiceComboBox extService = new ExtServiceComboBox(notCustomComment);
        extService.setLabel("Сервис");
        result.add(extService);

        IntegerField commentCount = new IntegerField("Комментариев");
        commentCount.setValue(1);
        result.add(commentCount);

        IntegerField mediaCount = new IntegerField("На последние посты");
        result.add(mediaCount);

        ButtonWithLog addButton = new ButtonWithLog("Добавить", () -> {
            ioTaskService.get().orderForLastMedias(getParentBean(), mediaCount.getValue(),
                    mediaBean -> order(mediaBean, extService, commentCount.getValue()));
            mediaCount.setValue(null);
            commentCount.setValue(1);
            taskListHolder.refresh();
        });
        ButtonWithLog addSelectedButton = new ButtonWithLog("Добавить на выбранные", () -> {
            Set<MediaBean> selectedItems = grid.asMultiSelect().getSelectedItems();
            for (MediaBean selectedItem : selectedItems) {
                order(selectedItem, extService, commentCount.getValue());
            }
            taskListHolder.refresh();
        });

        result.add(addButton, addSelectedButton);
        result.setAlignSelf(Alignment.END, addButton);
        result.setAlignSelf(Alignment.END, addSelectedButton);
        return result;
    }

    private void order(MediaBean mediaBean, ExtServiceComboBox extService, Integer count) {
        CheatTaskBean value = extService.getValue();
        ioTaskService.get().orderComments(mediaBean.getCode(), mediaBean, getParentBean(), value,
                value != null && (count == null || value.getMin() > count) ? value.getMin() : count, "", null, new Date());

    }

}
