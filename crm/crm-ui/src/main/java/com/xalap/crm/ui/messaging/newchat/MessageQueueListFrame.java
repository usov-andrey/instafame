/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.newchat;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.MessageChannel;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.MessageType;
import com.xalap.vaadin.custom.component.ClipboardHelper;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.custom.grid.dataprovider.GridSqlDataProvider;
import com.xalap.vaadin.starter.ui.MainLayout;

import java.util.List;

import static com.xalap.framework.domain.page.dataprovider.PageableDataProvider.page;

/**
 * Сообщения, которые не были автоматически отправлены и требуют ручной отправки
 *
 * @author Usov Andrey
 * @since 02/02/2020
 */
@Route(value = MessageQueueListFrame.VIEW_NAME, layout = MainLayout.class)
public class MessageQueueListFrame extends RootListFrame<MessageBean> {

    public static final String VIEW_NAME = "messageQueue";

    public MessageQueueListFrame(MessageService service, ContactService contactService) {
        super(aClass -> new GridPanel<>(MessageBean.class));
        /*
        gridPanel.dataSource().setMemoryDataProvider(new Supplier<Collection<MessageBean>>() {
            @Override
            public Collection<MessageBean> get() {
                List<MessageBean> byMessageType = service.repository().findByMessageType(MessageType.manual_sending);
                contactService.load(byMessageType, messageBean -> messageBean.getThread().getContact().getId(),
                        (messageBean, contactBean) -> messageBean.getThread().setContact(contactBean));
                return byMessageType;
            }
        });*/

        gridPanel.dataSource().setPageableDataProvider(new GridSqlDataProvider<>(
                        () -> page(
                                pageable -> {
                                    List<MessageBean> messageBeanPage = service.repository().findByMessageType(MessageType.manual_sending,
                                            service.pageable(pageable));
                                    contactService.load(messageBeanPage, messageBean -> messageBean.getThread().getContact().getId(),
                                            (messageBean, contactBean) -> messageBean.getThread().setContact(contactBean));
                                    return messageBeanPage;
                                },
                                () -> service.repository().countByMessageType(MessageType.manual_sending)
                        ),
                        GridDefaultSorting.desc(MessageBean.CREATE_TIME)
                )
        );
        GridColumns<MessageBean> columns = gridPanel.columns();
        columns.add(MessageBean.CREATE_TIME);
        columns.add("Имя", messageBean -> messageBean.getThread().getContact().getName());
        columns.add(MessageBean.TEXT);
        columns.addComponent("Контакт", messageBean -> {
            if (messageBean.getChannel() == MessageChannel.instagram && messageBean.getContactData() != null) {
                return new InstagramComponent(messageBean.getContactData());
            } else {
                return new Label();
            }
        });

        columns.actions((actions, bean) -> {
            actions.add(new ClipboardHelper(bean.getText(), new Button("Скопировать")));
        });
    }
}
