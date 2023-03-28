/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.lead;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.quiz.QuizBean;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.crm.ui.contact.ContactFrame;
import com.xalap.crm.ui.order.OrderFrame;
import com.xalap.crm.ui.pipeline.stage.PipelineStageFrame;
import com.xalap.crm.ui.quiz.QuizFrame;
import com.xalap.framework.utils.DateHelper;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.grid.BeanWithIdGridPanel;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.route.RouterLink;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Map;

/**
 * @author Усов Андрей
 * @since 14/09/2019
 */
public class LeadGridPanel extends BeanWithIdGridPanel<LeadBean, Integer> {

    public LeadGridPanel(ServiceRef<QuizService> quizService, ServiceRef<OrderService> orderService) {
        super(LeadBean.class);
        sortWithId();

        Map<LeadBean, QuizBean> quizMap = quizService.get().getQuizMap();
        Map<LeadBean, OrderBean> orderMap = orderService.get().getOrderByLeadMap();

        GridColumns<LeadBean> columns = columns();
        columns.addLink("Создание", leadBean -> DateHelper.getDateTime(leadBean.getCreateTime()),
                LeadBean::getId, LeadFrame.class);
        columns.add(LeadBean.NAME1);
        columns.addComponent("Инстаграм", leadBean -> new InstagramComponent(leadBean.getInstagram()));
        columns.addComponent("Источник", leadBean -> {
            if (leadBean.getSource().contains("Quiz")) {
                QuizBean quizBean = quizMap.get(leadBean);
                if (quizBean != null) {
                    return new RouterLink(leadBean.getSource(), QuizFrame.class, quizBean.getId());
                }
            } else if (leadBean.getSource().contains("Заказ")) {
                OrderBean orderBean = orderMap.get(leadBean);
                if (orderBean != null) {
                    return new RouterLink(leadBean.getSource(), OrderFrame.class, orderBean.getId());
                }
            }
            return new Label(leadBean.getSource());
        });
        columns.add(LeadBean.EMAIL, LeadBean.CLIENT_ID);
        columns.addLink("Контакт", leadBean -> leadBean.getContact() != null ? leadBean.getContact().getName() : "",
                (ValueProvider<LeadBean, Integer>) leadBean -> leadBean.getContact() != null ? leadBean.getContact().getId() : 0, ContactFrame.class);
        columns.addLink("Этап", leadBean -> leadBean.getStage() != null ? leadBean.getStage().getName() : "",
                leadBean -> leadBean.getStage().getId(), PipelineStageFrame.class);
    }
}
