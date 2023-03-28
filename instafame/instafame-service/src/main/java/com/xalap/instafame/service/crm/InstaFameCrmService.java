/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.crm;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.MessageChannel;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.MessageType;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import com.xalap.crm.service.quiz.QuizBean;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.InstaFameCheckApiTask;
import com.xalap.instafame.service.InstaFameService;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 03/08/2019
 */
@Service
public class InstaFameCrmService {

    public static final String FINISH_QUIZ = "finishQuiz";
    public static final String QUIZ_BONUS_SENT = "quizBonusSend";
    public static final String GOTO_ORDER = "gotoOrder";
    public static final String ORDER_PAYED = "orderPayed";
    public static final String ORDER_NOT_PAYED = "orderNotPayed";
    public static final String INSTAGRAM_CONFIRM_SENT = "instagramConfirmSent";
    public static final String ORDER_START = "orderStart";
    public static final String ORDER_START_SENT = "orderStartSent";
    public static final String ORDER_FINISH = "orderFinish";
    public static final String FORGOT_PAYMENT = "forgotPayment";
    public static final String FORGOT_PAYMENT_SENT = "forgotPaymentSent";
    private static final Logger log = LoggerFactory.getLogger(InstaFameCrmService.class);
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private LeadService leadService;
    @Autowired
    private PipelineStageService stageService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private InstaFameService instaFameService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private InstagramUserService userService;
    @Autowired
    private IOService instaOrderService;
    @Autowired
    private OrderService orderService;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        schedulerService.register(CrmPipelineSchedulerTask.class, "Переход заявок по воронке");
        schedulerService.register(InstaFameCheckApiTask.class, "Проверка api");
    }

    /**
     * Обрабатываем воронку
     */
    public void processPipeline() {
        //sendQuizBonuses();
        orderPayed();
        forgotPay();
        orderStarted();
    }

    /**
     * Получен заказ, нужно отправить письмо о начале выполнения заказа
     */
    private void orderStarted() {
        log.debug("Обрабатываем этап: " + InstaFameCrmService.ORDER_START);
        for (LeadBean lead : getLeads(InstaFameCrmService.ORDER_START)) {
            runAndChangeStage(() -> {
                instaFameService.sendEmailAboutStartOrder(lead);
                //Не окупается считывание подпичиков пользователей
                //Никто не спрашивает, а вот запросы api инстаграм мы забиваем
                //ioService.readUserLastFollowers(lead);
            }, lead, InstaFameCrmService.ORDER_START_SENT);
        }
    }

    private void forgotPay() {
        log.debug("Обрабатываем этап: " + InstaFameCrmService.GOTO_ORDER);
        Date before30Minutes = DateHelper.incMinutes(new Date(), -30);
        for (LeadBean lead : getLeads(InstaFameCrmService.GOTO_ORDER)) {
            if (lead.getStageTime() != null && before30Minutes.after(lead.getStageTime())) {
                //Если прошло 30 минут с нахождения заявки в этом статусе, то отправляем письмо о забытой корзине
                //и переводим в статус отправлено
                runAndChangeStage(() -> instaFameService.sendEmailForgotPayment(lead), lead,
                        InstaFameCrmService.FORGOT_PAYMENT_SENT);
            }
        }
    }

    private void orderPayed() {
        log.debug("Обрабатываем этап: " + InstaFameCrmService.ORDER_PAYED);
        for (LeadBean lead : getLeads(InstaFameCrmService.ORDER_PAYED)) {
            confirmOrder(lead);
        }
    }

    private void confirmOrSendConfirmation(LeadBean lead) {
        //Если это первый заказ на этот инстаграм аккаунт, то нужно отправить письмо о подтверждении инстаграм
        //Иначе нужно сразу начинать заказ
        Optional<InstagramUserBean> user = userService.getOrCreateUser(lead.getInstagram());
        if (user.isPresent()) {
            //Если уже был исполнен заказ на этот инстаграм, то подтверждение не нужно
            if (instaOrderService.existsStartedOrder(user.get()) &&
                    confirmOrder(lead)) {
                return;
            }
        }
        sendConfirmInstagramEMail(lead);
    }

    private boolean confirmOrder(LeadBean lead) {
        OrderBean orderBean = orderService.repository().findByLead(lead);
        if (orderBean != null) {
            instaFameService.confirmOrder(orderBean, lead);
            return true;
        }
        return false;
    }

    /**
     * Отправляем письмо для подтверждения аккаунта
     */
    private void sendConfirmInstagramEMail(LeadBean lead) {
        runAndChangeStage(() -> {
            instaFameService.sendEmailAboutConfirmInstagram(lead);
            //Нужно другие заявки этого контакта, находящиеся в статусе ожидает Оплаты, перевести в статус Не оплачено.
            for (LeadBean leadBean : leadService.repository().findByContact(lead.getContact())) {
                if (leadBean.getStage() != null) { //Старые заявки не ходили по этапам
                    log.debug("Проверяем заявку на необходимость перевода на этап неоплачено:" + leadBean);
                    if (InstaFameCrmService.GOTO_ORDER.equals(leadBean.getStage().getCode())) {
                        leadService.changeStage(leadBean, InstaFameCrmService.ORDER_NOT_PAYED);
                    }
                }
            }
        }, lead, InstaFameCrmService.INSTAGRAM_CONFIRM_SENT);
    }

    private void sendQuizBonuses() {
        log.debug("Обрабатываем этап: " + InstaFameCrmService.FINISH_QUIZ);
        for (LeadBean lead : getLeads(InstaFameCrmService.FINISH_QUIZ)) {
            QuizBean quizBean = quizService.repository().findByLead(lead);
            List<MessageBean> messageList = messageService.getMessageList(lead.getContact(), MessageChannel.instagram);
            boolean askForGift = false;
            for (MessageBean messageBean : messageList) {
                String text = messageBean.getText();
                if (messageBean.getMessageType() == MessageType.inbound && (
                        (text.contains("подаро") || text.contains("ПОДАРО"))
                                && (text.contains("хочу") || text.contains("ХОЧУ")))) {
                    askForGift = true;
                    break;
                }
            }
            if (askForGift) {
                //Возможно уже сообщение отправлено в ручном режиме
                for (MessageBean messageBean : messageList) {
                    String text = messageBean.getText();
                    if (messageBean.getMessageType() == MessageType.outbound && text.contains("quiz-bonuses")) {
                        leadService.changeStage(lead, InstaFameCrmService.QUIZ_BONUS_SENT);
                        askForGift = false;
                        break;
                    }
                }
                if (askForGift) {
                    runAndChangeStage(() -> {
                        instaFameService.sendQuizBonusToInstagram(quizBean);
                    }, lead, InstaFameCrmService.QUIZ_BONUS_SENT);
                }
            }
        }
    }

    private List<LeadBean> getLeads(String stageCode) {
        PipelineStageBean stage = stageService.getByCode(stageCode);
        return leadService.repository().findByStage(stage);
    }

    private void runAndChangeStage(Runnable runnable, LeadBean lead, String stageCode) {
        try {
            runnable.run();
            leadService.changeStage(lead, stageCode);
        } catch (Exception e) {
            log.error("Error on run and move lead to stage, lead:" + lead + ", stageCode:" + stageCode, e);
        }
    }
}
