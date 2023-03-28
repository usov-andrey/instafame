/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.template;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.messaging.template.MessageTemplateBean;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;

/**
 * @author Усов Андрей
 * @since 07/06/2019
 */
@Route(value = MessageTemplateListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Шаблоны сообщений")
public class MessageTemplateListFrame extends RootEntityListFrame<MessageTemplateBean> {
    public static final String VIEW_NAME = "messageTemplateList";

    @Autowired
    public MessageTemplateListFrame(ServiceRef<MessageTemplateService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));
        gridPanel.columns()
                .add(MessageTemplateBean.NAME1, MessageTemplateBean.TEXT, MessageTemplateBean.CODE);
        addCreateButton();
    }

}
