/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import com.xalap.crm.ui.contact.ContactMessageListFrame;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.commenttemplate.CommentTemplateService;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.IOStatus;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.MemoryOrderIOTaskProvider;
import com.xalap.instafame.ui.instaorder.createcomments.IOCreateCommentsTab;
import com.xalap.instafame.ui.instaorder.createlikes.IOCreateLikesTab;
import com.xalap.instafame.ui.instaorder.createlikes.MediaListHolder;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskListHolder;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskListTab;
import com.xalap.instagram.service.media.MediaService;
import com.xalap.instagram.service.user.InstagramUserService;
import com.xalap.vaadin.custom.component.LabelList;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author Усов Андрей
 * @since 11/06/2019
 */
@Route(value = IOListFrame.VIEW_NAME, layout = MainLayout.class)
public class IOFrame extends RootEntityFrame<IOBean, Integer> {

    private final IOTaskListHolder taskListHolder;

    @Autowired
    public IOFrame(ServiceRef<IOService> service, ServiceRef<IOTaskService> ioTaskService,
                   ServiceRef<InstagramUserService> userService, ServiceRef<MediaService> mediaService,
                   ServiceRef<CommentTemplateService> commentTemplateService) {
        super(service, IOListFrame.class);

        taskListHolder = new IOTaskListHolder(() -> new MemoryOrderIOTaskProvider(ioTaskService.get().repository()));
        MediaListHolder mediaListHolder = new MediaListHolder();

        addListener(value -> {
            mediaListHolder.setValue(
                    value.getUser() != null ? new ArrayList<>(mediaService.get().getUserLastMediaList(value.getUser()))
                            : new ArrayList<>()
            );
            taskListHolder.setValue(value);
        });

        UserInfoComponent userInfo = new UserInfoComponent();
        LabelList status = new LabelList();

        taskListHolder.addListener(bean -> {
            userInfo.setUser(bean.getInstagram(), bean.getUser(), userService, userBean -> {
                if (bean.getUser() == null) {
                    bean.setUser(userBean);
                    service.get().save(bean);
                }
            });
            if (bean.getOrder() != null) {
                userInfo.add(new Button("Написать", event ->
                        navigate(ContactMessageListFrame.class, bean.getOrder().getLead().getContact().getId())));
            }
            IOStats stats = taskListHolder.getStats();

            String profit = bean.getOrder() != null && bean.getOrder().getRevenue() != null ? "Прибыль: " + StringHelper.toString(bean.getOrder().getRevenue() - stats.getCharge())
                    + " Доход: " + StringHelper.toString(bean.getOrder().getRevenue()) +
                    " Расход: " + StringHelper.toString(stats.getCharge()) :
                    "";
            status.clearAndAddText(bean.statusCaption() + " " + bean.getFollowersPackage().name()
                            + ": " + stats.getRealFollowers().captionFull(),

                    " Подписчиков: " + stats.getFollowers().captionFull() +
                    " Лайков: " + stats.getLikes().captionFull() + " Комментариев: " + stats.getComments().captionFull()
                            + " Заданий в работе: " + stats.getInProgressCount(),
                    profit
            );
            if (bean.getStatus() == IOStatus.Refilling) {
                int maxPercent = bean.getRestoreMaxPercent() != null ?
                        bean.getRestoreMaxPercent() :
                        ioTaskService.get().getSettings().getRestoreMaxPercent();

                int followersMaxPercent = bean.refillingHours().currentPercent() * (maxPercent - 100) / 100 + 100;
                //Какой процент у него сейчас относительно идеального(если бы не отписывались подписчики)
                int realFollowerPercent = stats.getFollowers().currentPercent();

                status.addText("Восстановление подписчиков. Выдано:" + realFollowerPercent + "% По плану:" + followersMaxPercent + "% Макс:" + maxPercent + "%");
            }
        });

        setViewContent(withTabs()
                .beforeTabs(
                        userInfo, status
                )
                .addMainTab(new IOForm(service))
                .addTab("Задачи", new IOTaskListTab(service, ioTaskService, taskListHolder))
                .addTab("Задать комментарии", new IOCreateCommentsTab(ioTaskService, mediaListHolder,
                        taskListHolder, commentTemplateService))
                .addTab("Лайки", new IOCreateLikesTab(ioTaskService, mediaListHolder, taskListHolder))
        );

    }

    @Override
    protected String getTitle() {
        String instagram = getBean().getInstagram();
        return instagram != null ? instagram : "Аккаунт не задан";
    }


}
