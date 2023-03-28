/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.quiz;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.quiz.QuizBean;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.crm.ui.contact.ContactMessageListFrame;
import com.xalap.crm.ui.lead.LeadFrame;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridActions;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.custom.grid.valueprovider.DateTimeValueProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;
import static com.xalap.vaadin.custom.grid.renderer.LabelRendererBuilder.label;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = QuizListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Quiz")
public class QuizListFrame extends RootEntityListFrame<QuizBean> {
    public static final String VIEW_NAME = "quizList";

    @Autowired
    public QuizListFrame(ServiceRef<QuizService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));
        GridColumns<QuizBean> columns = gridPanel.columns();
        columns.addColumn("Время", new DateTimeValueProvider<>((bean) -> bean.getLead().getCreateTime()))
                .setAutoWidth(false).setWidth("120px");
        columns.addLink("Заявка", bean -> bean.getLead() != null ? bean.getLead().getName() : "",
                bean -> bean.getLead() != null ? bean.getLead().getId() : 0, LeadFrame.class).setAutoWidth(false).setWidth("100px");
        columns.addComponent("Инстаграм", bean -> bean.getLead() != null && bean.getLead().getInstagram() != null ?
                new InstagramComponent(bean.getLead().getInstagram()) : new Label()).setAutoWidth(false).setWidth("150px");
        gridPanel.columnBuilder().add("Ответы",
                label("Пол", QuizBean::getSex),
                label("Опыт", QuizBean::getExperience),
                label("Подарок", QuizBean::getGiftType),
                label("Стоимость", QuizBean::getPrice1000),
                label("Стратегия", QuizBean::getStrategy)
        ).setAutoWidth(false).setWidth("300px");

        columns.actions(this::setActions);

        addCreateButton();
    }

    protected void setActions(GridActions<QuizBean> actions, QuizBean quizBean) {
        actions.
                add("Написать", () -> navigate(ContactMessageListFrame.class,
                        quizBean.getLead().getContact().getId()));
    }


}