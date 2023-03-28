/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.createlikes;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.framework.utils.MinMax;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.task.*;
import com.xalap.instafame.ui.instaorder.components.ExtServiceComboBox;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskListHolder;
import com.xalap.instagram.api.media.MediaType;
import com.xalap.instagram.service.media.MediaBean;
import com.xalap.instagram.ui.MediaComponent;
import com.xalap.vaadin.custom.component.EnumCountList;
import com.xalap.vaadin.custom.component.IntegerField;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.tab.Tab;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Усов Андрей
 * @since 18/05/2019
 */
public class IOCreateLikesTab extends Tab<IOBean> {

    public static final String WIDTH = "250px";
    private final ServiceRef<IOTaskService> ioTaskService;
    private final IOTaskListHolder tasksProvider;
    private final Set<MediaBean> selectedMedia = new HashSet<>();

    public IOCreateLikesTab(ServiceRef<IOTaskService> ioTaskService, MediaListHolder mediaListHolder,
                            IOTaskListHolder tasksProvider) {
        this.ioTaskService = ioTaskService;
        this.tasksProvider = tasksProvider;

        EnumCountList<InstaOrderTaskStatus, IOLikesTaskBean> countList =
                new EnumCountList<>(InstaOrderTaskStatus.class, tasksProvider::getLikesList, IOTaskBean::getStatus,
                        ioLikesTaskBeen -> ioLikesTaskBeen.stream().mapToInt(IOTaskBean::getQuantity).sum());
        tasksProvider.addListener(countList::redraw);
        add(countList);

        add(createFastOrderPanel());

        GridPanel<MediaRow> gridPanel = new GridPanel<>(MediaRow.class);
        gridPanel.dataSource().setMemoryDataProvider(() -> {
            List<MediaBean> userMediaList = mediaListHolder.getValue();
            return getMediaRowList(userMediaList, 3);
        });
        //В случае изменении задач данные по медиа не изменяются
        tasksProvider.addListener(value -> gridPanel.reDraw());
        add(gridPanel);

        GridColumns<MediaRow> columns = gridPanel.columns();
        columns.addComponent("", mediaBeen -> createMediaComponent(mediaBeen, 0)).setWidth(WIDTH);
        columns.addComponent("", mediaBeen -> createMediaComponent(mediaBeen, 1)).setWidth(WIDTH);
        columns.addComponent("", mediaBeen -> createMediaComponent(mediaBeen, 2)).setWidth(WIDTH);
    }

    private Component createFastOrderPanel() {
        FlexHorizontalLayout result = new FlexHorizontalLayout();
        ExtServiceComboBox extService = createLikesService();
        extService.setLabel("Сервис");
        result.add(extService);
        IntegerField countField = new IntegerField("Количество");
        tasksProvider.addListener((bean) -> {
            IOStats.Stat stat = tasksProvider.getStats().getLikes();
            int percent = bean.progressHours().currentPercent() + 15;
            if (stat.getMax() > 0 && percent > stat.plannedPercent()) {
                if (percent > 100) {
                    percent = 100;
                }
                int count = stat.getMaxMinusPlanned() * percent / 100;
                if (count > 0) {
                    countField.setValue(count);
                }
            }
        });
        extService.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                countField.setPlaceholder("Min:" + event.getValue().getMin());
            }
        });
        result.add(countField);
        IntegerField mediaCount = new IntegerField("На последние посты");
        result.add(mediaCount);

        Button addButton = new Button("Добавить", event -> {
            if (countField.getValue() == null || mediaCount.getValue() == null) {
                return;
            }
            int likesForMedia = countField.getValue() / mediaCount.getValue();
            addLikes(extService, countField, mediaCount, likesForMedia);
        });
        result.add(addButton);

        Button addToSelectedButton = new Button("Добавить на выбранные", event -> {
            if (countField.getValue() == null || selectedMedia.isEmpty()) {
                return;
            }
            int mediaCount1 = selectedMedia.size();
            int likesForMedia = countField.getValue() / mediaCount1;
            orderLikesWithView(extService, countField, likesForMedia);
        });
        result.add(addToSelectedButton);

        result.setAlignSelf(Alignment.END, addButton, addToSelectedButton);
        return result;
    }

    private void orderLikesWithView(ExtServiceComboBox extService, IntegerField countField, int likesForMedia) {
        try {
            for (MediaBean mediaBean : selectedMedia) {
                ioTaskService.get().orderLikesWithViews(mediaBean, getParentBean(), extService.getValue(),
                        likesForMedia, new MinMax(0, 10),
                        null);
            }
            countField.clear();
            tasksProvider.refresh();
        } catch (NotLoadedMediaException e) {
            UIUtils.showNotification(e.getMessage());
        }
    }

    private void addLikes(ExtServiceComboBox extService, IntegerField countField, IntegerField mediaCount, int likesForMedia) {
        try {
            ioTaskService.get().orderForLastMedias(getParentBean(), mediaCount.getValue(),
                    mediaBean -> ioTaskService.get().orderLikesWithViews(mediaBean, getParentBean(), extService.getValue(),
                            likesForMedia, new MinMax(0, 10),
                            null));
            countField.clear();
            mediaCount.clear();
            tasksProvider.refresh();
        } catch (NotLoadedMediaException e) {
            UIUtils.showNotification(e.getMessage());
        }
    }

    private Component createMediaComponent(MediaRow row, int index) {
        if (row.size() <= index) {
            return new Label();
        }
        MediaBean mediaBean = row.get(index);
        VerticalLayout layout = new VerticalLayout();
        boolean isVideo = mediaBean.getType() == MediaType.video;
        layout.add(new MediaComponent(mediaBean, "250px"));
        layout.add(new Label("Тип:" + mediaBean.getType().getCaption() +
                (isVideo ? " Просмотры: " + mediaBean.getStats().getViewVideoCount() : "") +
                " Лайков:" + mediaBean.getStats().getLikesCount()));
        createOrderLikesPanel(layout, mediaBean, createLikesService());
        return layout;
    }

    private ExtServiceComboBox createLikesService() {
        ExtServiceComboBox comboBox = new ExtServiceComboBox(ioTaskService.get().getCheatTaskService(), InstaOrderTaskType.likes);
        parentBeanHolder.addListener(value -> comboBox.setDefaultValue(() -> value.getFollowersPackage()
                .getLikesTask(ioTaskService.get().getSettings())));
        return comboBox;
    }

    private void createOrderLikesPanel(VerticalLayout verticalLayout, MediaBean mediaBean, ExtServiceComboBox extService) {

        verticalLayout.add(extService);

        FlexHorizontalLayout layout = new FlexHorizontalLayout();
        IntegerField integerField = new IntegerField("");
        layout.add(integerField);
        layout.add(new Button("Добавить", event -> {
            ioTaskService.get().orderLikesWithViews(mediaBean, parentBeanHolder.getValue(), extService.getValue(),
                    integerField.getValue(), new MinMax(0, 0), null);
            integerField.setValue(null);
            tasksProvider.refresh();
        }));
        Checkbox checkbox = new Checkbox();
        checkbox.addValueChangeListener(checkboxBooleanComponentValueChangeEvent -> {
            if (Boolean.TRUE.equals(checkboxBooleanComponentValueChangeEvent.getValue())) {
                selectedMedia.add(mediaBean);
            } else {
                selectedMedia.remove(mediaBean);
            }
        });
        layout.add(checkbox);
        verticalLayout.add(layout);

        extService.addValueChangeListener( event -> {
            if (event.getValue() != null) {
                integerField.setPlaceholder("Min:" + event.getValue().getMin());
            }
        });

    }

    private List<MediaRow> getMediaRowList(List<MediaBean> userMediaList, int size) {
        List<MediaRow> result = new ArrayList<>();
        MediaRow row = null;
        int index = size;
        for (MediaBean mediaBean : userMediaList) {
            if (index == size) {
                index = 0;
                row = new MediaRow();
                result.add(row);
            }
            row.add(mediaBean);
            index++;
        }
        return result;
    }

}
