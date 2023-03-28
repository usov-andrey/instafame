/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.newchat;

import com.github.appreciated.card.StatefulCard;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.content.ItemBody;
import com.github.appreciated.card.label.SecondaryLabel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.messaging.thread.MessageThreadBean;
import com.xalap.crm.service.messaging.thread.MessageThreadService;
import com.xalap.framework.utils.DateHelper;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = MessageThreadListFrame.VIEW_NAME, layout = MainLayout.class)
public class MessageThreadListFrame extends RootListFrame<MessageThreadBean> {

    public static final String VIEW_NAME = "messageThreads";

    @Autowired
    public MessageThreadListFrame(ServiceRef<MessageThreadService> service, ServiceRef<MessageService> messageService,
                                  ServiceRef<EmailService> emailService,
                                  ServiceRef<MessageTemplateService> messageTemplateService,
                                  ServiceRef<InstagramDirectService> instagramDirectService) {
        super((bClass) -> new GridPanel<>(MessageThreadBean.class));
        //Окно при клике на строку грида
        MessageListWindow detailsWindow = new MessageListWindow(messageService, emailService,
                messageTemplateService, instagramDirectService);

        Grid<MessageThreadBean> grid = gridPanel.grid();
        //При выборе строки грида мы открываем окно чата
        gridPanel.setSelectionListener(threadBean -> detailsWindow.showDetails(service.get().messageThreadProvider(threadBean)));

        //Отображение
        grid.addComponentColumn(threadBean -> createMessageThreadComponent(grid, threadBean));

        setDataProvider(service, GridDefaultSorting.desc(MessageThreadBean.LAST_MESSAGE_TIME));

        gridPanel.buttons().add("Обновить", instagramDirectService.get()::asyncUpdateAndAccept);

        setViewDetails(detailsWindow.createDetailsDrawer());
    }

    private Component createMessageThreadComponent(Grid<MessageThreadBean> grid, MessageThreadBean threadBean) {
        Div img = new Div();
        img.getStyle().set("background", "var(--lumo-primary-color)");
        img.setWidth("50px");
        img.setHeight("50px");
        img.getStyle().set("border-radius", "100%");

        StatefulCard card = new StatefulCard(event -> {
            grid.select(threadBean);
        },
                new IconItem(img, new ItemBody(threadBean.getContact().getName(), threadBean.getLastMessageText())),
                new SecondaryLabel(DateHelper.getDateTime(threadBean.getLastMessageTime()))
        );

        card.setElevation(0);
        card.setWidth("100%");
        return card;
    }

}
