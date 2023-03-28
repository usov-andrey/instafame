/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.quiz;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.quiz.QuizBean;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
@Route(value = QuizListFrame.VIEW_NAME, layout = MainLayout.class)
public class QuizFrame extends RootEntityFrame<QuizBean, Integer> {

    @Autowired
    public QuizFrame(ServiceRef<QuizService> service) {
        super(service, QuizListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
        );
    }


    @Override
    protected String getTitle() {
        return "Quiz пройденный от " + getBean().getLead().getName();
    }
}
