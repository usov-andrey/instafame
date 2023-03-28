/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.crm;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.quiz.QuizBean;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.crm.ui.quiz.QuizListFrame;
import com.xalap.instafame.service.InstaFameService;
import com.xalap.instafame.ui.component.ClipboardDialog;
import com.xalap.vaadin.custom.grid.GridActions;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Добавляем кнопки в стандартный грид
 * @author Usov Andrey
 * @since 2020-02-19
 */
@Route(value = InstaFameQuizListFrame.VIEW_NAME, layout = MainLayout.class)
public class InstaFameQuizListFrame extends QuizListFrame {

    public static final String VIEW_NAME = "instaQuizList";

    private final ServiceRef<InstaFameService> instaFameService;

    @Autowired
    public InstaFameQuizListFrame(ServiceRef<QuizService> service, ServiceRef<InstaFameService> instaFameService) {
        super(service);
        this.instaFameService = instaFameService;
    }

    @Override
    protected void setActions(GridActions<QuizBean> actions, final QuizBean quizBean) {
        super.setActions(actions, quizBean);
        actions.add("Отправить E-mail c бонусами", () -> {
            instaFameService.get().sendQuizBonusEmail(quizBean);
        });

        actions.add("Ссылка с бонусами", () -> copy(quizBean));
    }

    private void copy(QuizBean quizBean) {
        InstaFameService service = this.instaFameService.get();
        service.sendQuizBonusToInstagram(quizBean);
        String quizGiftText = service.getQuizGiftText(quizBean);
        ClipboardDialog.open(quizGiftText);
    }
}
