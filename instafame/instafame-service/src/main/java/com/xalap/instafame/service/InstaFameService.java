/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service;

import com.xalap.crm.service.contact.*;
import com.xalap.crm.service.integration.robokassa.RobokassaPayment;
import com.xalap.crm.service.integration.robokassa.RobokassaService;
import com.xalap.crm.service.integration.sendgrid.templates.Template;
import com.xalap.crm.service.integration.tilda.TildaForm;
import com.xalap.crm.service.integration.tilda.TildaPayment;
import com.xalap.crm.service.integration.tilda.TildaService;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.lead.LeadWithContactData;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.email.EmailAttributes;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.OrderStatus;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.crm.service.product.ProductService;
import com.xalap.crm.service.quiz.QuizBean;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.framework.exception.ClientErrorException;
import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.utils.RandomUtils;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.crm.InstaFameCrmService;
import com.xalap.instafame.service.instaorder.CreateIOBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOFollowersSpeed;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.instagram.service.account.InstagramAccountProvider;
import com.xalap.instagram.service.api.InstagramClient;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import com.xalap.uaa.api.account.AccountApi;
import com.xalap.uaa.api.exception.EmailAlreadyUsedException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * instafame.ru
 *
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Service
@AllArgsConstructor
public class InstaFameService {

    public static final String CART_FORM_ID = "form75782285";
    public static final String CONFIRM_INSTAGRAM_FORM_ID = "form83475297";
    public static final String QUIZ_FORM_ID = "form81855435";
    public static final String CONTACT_FORM_ID = "form72681656";
    public static final String FOLLOWERS_100K_FORM_ID = "form98626640";
    public static final String ROBOKASSA_SHOP_ID = "instafame";
    //Для тестирования формы заказа, в этом случае не уходит информация в crm или базу
    private static final String TEST_EMAIL = "test@mail.ru";
    private static final String TEST_NAME = "test";

    private static final Logger log = LoggerFactory.getLogger(InstaFameService.class);
    private static final String SEND_PROMO_PARAM = "SEND_PROMO";

    private final TildaService tildaService;
    private final RobokassaService robokassaService;
    private final NotificationService notificationService;
    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final LeadService leadService;
    private final QuizService quizService;
    private final ContactService contactService;
    private final ContactDataService contactDataService;
    private final IOService instaOrderService;
    private final PipelineStageService stageService;
    private final EmailService emailService;
    private final InstagramUserService userService;
    private final MessageTemplateService messageTemplateService;
    private final InstagramDirectService instagramDirectService;
    private final ProductService productService;
    private final SchedulerService schedulerService;
    private final AccountApi accountController;
    private final InstagramAccountProvider instagramAccountProvider;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        tildaService.addRequestHandler(CART_FORM_ID, this::gotoOrder);
        tildaService.addRequestHandler(QUIZ_FORM_ID, this::finishQuiz);
        tildaService.addRequestHandler(CONTACT_FORM_ID, this::giveContact);
        tildaService.addRequestHandler(CONFIRM_INSTAGRAM_FORM_ID, this::confirmInstagram);
        tildaService.addRequestHandler(FOLLOWERS_100K_FORM_ID, this::followers100k);
        tildaService.addRequestHandler("form175487966", this::registration);
        tildaService.addRequestHandler("form178715407", this::resetPassword);
        robokassaService.addListener(ROBOKASSA_SHOP_ID, this::pay);

        //Создаем продукты, если не созданы
        createProduct("economyFollowers", "Эконом подписчики", 0.34);
        createProduct("premiumFollowers", "Премиум подписчики", 0.94);
        createProduct("vipFollowers", "VIP пакет", 1.44);
        createProduct("likes", "Лайки", 0.3);
        createProduct("comments", "Комментарии", 20);

        if (schedulerService != null) {
            schedulerService.register(UpdateInstagramUsersTask.class, "Обновление информации о инстаграм пользователях");
        }
    }

    private void createProduct(String code, String name, double price) {
        ProductBean productBean = productService.getOrCreateProduct(code);
        if (productBean.getName().equals(productBean.getCode())) {
            productBean.setName(name);
            productBean.setPrice(price);
            productService.save(productBean);
        }
    }

    private void followers100k(String requestId, TildaForm form) {
        PipelineStageBean stageBean = stageService.getByCode("100k");
        LeadWithContactData lead = tildaService.getOrCreateLead(requestId, form, "100k followers", stageBean, createLead(form));
        //instaFameCrmService.createLeadContact(instaFameCrmService.amoCrmClient(), lead);
    }

    /**
     * Оставил контакт
     */
    private void giveContact(String requestId, TildaForm form) {
        PipelineStageBean stageBean = stageService.getByCode("subscribeEmail");
        LeadWithContactData lead = tildaService.getOrCreateLead(requestId, form, "Подписка по e-mail", stageBean, createLead(form));
        //instaFameCrmService.createLeadContact(instaFameCrmService.amoCrmClient(), lead);
    }

    private void finishQuiz(String requestId, TildaForm form) {
        PipelineStageBean stageBean = stageService.getByCode(InstaFameCrmService.FINISH_QUIZ);
        LeadWithContactData lead = tildaService.getOrCreateLead(requestId, form, "Прошел Quiz", stageBean, createLead(form));
        getOrCreateQuiz(form, lead.getLeadBean());
        //instaFameCrmService.createLeadQuiz(instaFameCrmService.amoCrmClient(), lead, form);
    }

    public String getForgotOrderText(OrderBean orderBean) {
        String messageCode = "forgotOrder" + random1Or2();
        if (messageTemplateService.getTemplateByCode(messageCode).isEmpty()) {
            throw new ClientErrorException("Не найден шаблон сообщения с кодом: " + messageCode);
        }
        return messageTemplateService.process(messageCode, orderBean.getLead());
    }

    private int random1Or2() {
        return (RandomUtils.nextInt(1) + 1);
    }

    /**
     * Отправляем бонусы за quiz на email
     */
    public void sendQuizBonusEmail(QuizBean quizBean) {
        Template template = emailService.getTemplate("d-76ca6e63de9941308d709fc0104fd0bd");
        emailService.sendMessage(quizBean.getLead(), template, new Consumer<EmailAttributes>() {
            @Override
            public void accept(EmailAttributes emailProcessor) {
                emailProcessor.fill("QUIZ_BONUS", getQuizBonusCode(quizBean));
            }
        }, quizBean.getLead().getInstagram());
    }

    public String getQuizGiftText(QuizBean quizBean) {
        String messageCode = "quizBonus" + random1Or2();
        if (messageTemplateService.getTemplateByCode(messageCode).isEmpty()) {
            return "https://instafame.ru/quiz-bonuses?utm_bonus=" + getQuizBonusCode(quizBean);
        }
        return messageTemplateService.process(messageCode, quizBean.getLead(), messageTemplateService.keyword("bonusCode",
                getQuizBonusCode(quizBean)));
    }

    private String getQuizBonusCode(QuizBean quizBean) {
        String giftType = quizBean.getGiftType();
        return giftType.startsWith("Хочу быть блог") ? "Z7gbUxq"//Хочу быть блоггером
                : (giftType.startsWith("Видеокурс для новичков") ? "Hb3e91s" :
                "Anx22hda");
    }

    public void sendQuizBonusToInstagram(QuizBean quizBean) {
        LeadBean lead = quizBean.getLead();
        leadService.changeStage(lead, InstaFameCrmService.FINISH_QUIZ, InstaFameCrmService.QUIZ_BONUS_SENT);
        //ПРосто переводим заявку в новый статус
        //instagramDirectService.sendMessage(quizBean.getLead(), getQuizGiftText(quizBean));
    }

    public void sendEmailAboutConfirmInstagram(LeadBean leadBean) {
        OrderBean orderBean = orderService.repository().findByLead(leadBean);
        //List<IOBean> byOrder = instaOrderService.getByOrder(orderBean);
        Template template = emailService.getTemplate("d-57dae492aa0b4acdba74725baf500672");
        emailService.sendMessage(leadBean, template, new Consumer<EmailAttributes>() {
            @Override
            public void accept(EmailAttributes emailProcessor) {
                emailProcessor.fill("PAYMENTID", orderBean.getOrderId());
                /* В шаблоне письма этот параметр не пробрасывается и в javascript на сайте не обрабатывается
                if (byOrder.size() > 0) {//Если неверно указан instagram, то инста заказа может не быть
                    emailProcessor.fill("SPEED", Integer.toString(byOrder.get(0).getOrderStrategy().ordinal()));
                }
                 */
            }
        }, leadBean.getInstagram());
    }

    private void sendEmailWrongInstagram(LeadBean leadBean, String instagram) {
        Template template = emailService.getTemplate("d-0041370763de4fc4bffc45808d588e82");
        emailService.sendMessage(leadBean, template, instagram);
    }

    public void sendEmailAboutStartOrder(LeadBean leadBean) {
        OrderBean orderBean = orderService.repository().findByLead(leadBean);
        String templateCode = "d-54c1526ce0e547659326e58f1acafd0e";
        if (orderBean.getClientCost() >= 1000) {
            templateCode = "d-684addf4cbf74c4d9fdd4b6fed5820b4";
        }

        Template template = emailService.getTemplate(templateCode);
        emailService.sendMessage(leadBean, template, leadBean.getInstagram());
    }

    /**
     * Отправляем сообщение о забытой корзине в инстаграм
     */
    public void sendForgotPayToInstagram(LeadBean lead) {
        if (userService.findUser(lead.getInstagram()) != null) {
            String text = messageTemplateService.process("forgetOrder", lead.getContact());
            instagramDirectService.sendTemplate(lead.getContact(), lead.getInstagram(), text);
        }
    }

    public void sendEmailForgotPayment(LeadBean lead) {
        //notificationService.sendMessage("Отправляем сообщение о забытой корзине:" + lead);
    }

    private LeadBean createLead(TildaForm form) {
        LeadBean leadBean = new LeadBean();
        leadBean.setName(InstaFameField.name.fromForm(form));
        leadBean.setEmail(InstaFameField.email.fromForm(form));
        leadBean.setInstagram(InstaFameField.instagram.fromForm(form));
        return leadBean;
    }

    private QuizBean getOrCreateQuiz(TildaForm form, LeadBean leadBean) {
        QuizBean bean = quizService.repository().findByLead(leadBean);
        if (bean == null) {
            bean = new QuizBean();
            bean.setExperience(InstaFameField.experience.fromForm(form));
            bean.setGiftType(InstaFameField.giftType.fromForm(form));
            bean.setPrice1000(InstaFameField.price1000.fromForm(form));
            bean.setSex(InstaFameField.sex.fromForm(form));
            bean.setStrategy(InstaFameField.strategy.fromForm(form));
            bean.setLead(leadBean);
            log.debug("Create quiz:" + bean);
            bean = quizService.save(bean);
        }
        return bean;
    }

    private void gotoOrder(String requestId, TildaForm form) {
        //Для тестовых целей
        if (TEST_NAME.equals(InstaFameField.name.fromForm(form)) &&
                TEST_EMAIL.equals(InstaFameField.email.fromForm(form))) {
            return;
        }
        //заказ может быть такой уже есть
        TildaPayment payment = form.getPayment();
        String orderId = payment.getOrderid();
        String followersRate = InstaFameField.followersRate.fromForm(form);
        IOFollowersSpeed followersSpeed = StringHelper.isNotEmpty(followersRate) ? IOFollowersSpeed.getValue(followersRate)
                : IOFollowersSpeed.ByDay100_250;

        double paymentAmount = Double.parseDouble(payment.getAmount());
        String promoCode = payment.getPromocode();
        Integer discountPercent = null;
        String discountStringValue = payment.getDiscountvalue();
        if (StringHelper.isNotEmpty(discountStringValue)) {
            discountStringValue = discountStringValue.replace("%", "");
            discountPercent = Integer.parseInt(discountStringValue);
        }

        String comments = InstaFameField.comments.fromForm(form);
        createOrder(orderId, followersSpeed, paymentAmount, promoCode, discountPercent,
                pipelineStageBean -> getOrCreateLeadForOrder(requestId, form, orderId, pipelineStageBean),
                orderBean -> tildaService.getOrCreateOrderLines(form, orderBean), comments);
    }

    private LeadWithContactData getOrCreateLeadForOrder(String requestId, TildaForm form, String orderId, PipelineStageBean pipelineStageBean) {
        return tildaService.getOrCreateLead(requestId, form,
                "Заказ:" + orderId, pipelineStageBean, createLead(form), (leadBean -> {
                    ContactWithDataBean contactWithData = tildaService.getOrCreateContact(leadBean);
                    //Обновляем если нужно галочку, что пользователь готов принимать информацию о промо
                    if (form.has(SEND_PROMO_PARAM)) {
                        ContactBean contact = contactWithData.getContact();
                        contact.setCanSendPromo(true);
                        contactService.save(contact);
                    }
                    return contactWithData;
                }));
    }

    public OrderBean createOrder(String orderId, IOFollowersSpeed followersSpeed, double paymentAmount,
                                 String promoCode,
                                 Integer discountPercent,
                                 Function<PipelineStageBean, LeadWithContactData> leadCreator,
                                 Function<OrderBean, List<OrderLineBean>> orderBeanListFunction, String comments) {
        //заказ может быть такой уже есть
        OrderBean order = getOrder(orderId);
        if (order == null) {
            PipelineStageBean stageBean = stageService.getByCode(InstaFameCrmService.GOTO_ORDER);
            ////Лид может быть уже такой есть
            LeadWithContactData lead = leadCreator.apply(stageBean);

            order = orderService.createOrder(lead.getLeadBean(), paymentAmount, promoCode, discountPercent, orderId);
        }
        //Возможно позиции заказа уже есть
        List<OrderLineBean> orderLines = orderBeanListFunction.apply(order);

        //Возможно заказы уже созданы
        instaOrderService.getOrCreateOrders(new CreateIOBean(order, followersSpeed, comments), orderLines);
        return order;
    }


    private void pay(RobokassaPayment robokassaPayment) {
        OrderBean orderBean = getOrder(robokassaPayment.getInvoiceId());
        if (orderBean != null) {
            if (!orderBean.getLead().getEmail().equalsIgnoreCase(robokassaPayment.customerEmail())) {
                log.info("Robokassa client email is different with lead email:" + robokassaPayment +
                        "\nLead:" + orderBean.getLead());
                ContactBean contact = orderBean.getLead().getContact();
                contactDataService.saveContactData(contact, ContactDataType.email.data(robokassaPayment.customerEmail()));
            }
            if (orderBean.getStatus() == OrderStatus.Created) {
                orderBean.setCurrencyLabel(robokassaPayment.getCurrencyLabel());
                orderBean.setPaymentMethod(robokassaPayment.getPaymentMethod());
                payOrder(orderBean, robokassaPayment.payMinusFee(), robokassaPayment.customerEmail());

                //Отправляем в систему аналитики
                try {
                    orderService.sendToAnalytics(orderBean);
                } catch (Exception e) {
                    log.error("Ошибка при отправке данных в системы аналитики о платеже:" + orderBean, e);
                }
            } else {
                log.warn("Платеж уже обработан до этого: " + orderBean);
            }
            //Переводим лида в crm на следующий этап, если он еще не там
            //instaFameCrmService.makeLeadPayed(instaFameCrmService.amoCrmClient(), orderBean.getOrderId());
        } else {
            notificationService.sendMessage("Пришла оплата, но заказ по номеру оплаты не найден:" +
                    robokassaPayment.getInvoiceId() + " in robokassaPayment:" + robokassaPayment);
        }
    }

    public void payOrder(OrderBean orderBean, double revenue, String customerEmail) {
        orderBean.setRevenue(revenue);
        orderBean.setEmail(customerEmail);
        orderBean.setStatus(OrderStatus.Paid);
        orderService.save(orderBean);

        //Формируем информативное сообщение о заказе
        List<OrderLineBean> orderLines = orderLineService.getOrderLines(orderBean);
        String orderLinesSt = StringHelper.join(orderLines,
                orderLineBean -> orderLineBean.getLineText() +
                        "(" + orderLineBean.getQuantity() + ") = " +
                        StringHelper.toString(orderLineBean.getPrice()), ", ");
        notificationService.sendMessage("Получена оплата: " + orderBean.getRevenue() + " от " +
                orderBean.getLead().caption() + " за заказ: " + orderLinesSt);

        //Возможно insta заказа не было создано из-за ошибки и нужно, чтобы человек ждал,
        // когда подтвердит свой инстаграм через письмо
        instaOrderService.payOrder(orderBean);

        leadService.changeStage(orderBean.getLead(), InstaFameCrmService.ORDER_PAYED);
    }

    /**
     * Пользователь подтвердил свой инстаграм
     */
    private void confirmInstagram(String requestId, TildaForm tildaForm) {
        String instagram = InstaFameField.instagram.fromForm(tildaForm);
        String paymentId = InstaFameField.paymentId.fromForm(tildaForm);
        if (StringHelper.isEmpty(instagram) || StringHelper.isEmpty(paymentId)) {
            throw new IllegalStateException("Parameters instagram or paymentId is empty:" + tildaForm);
        }
        OrderBean orderBean = orderService.getOrderBean(paymentId);
        LeadBean leadBean = orderBean.getLead();
        //Возможно пользователь указал неверный инстаграм, тогда отправляем ему письмо об этом
        Optional<InstagramUserBean> user = userService.getOrCreateUser(instagram);
        if (user.isEmpty() && !instagramAccountProvider.isBlocked()) {
            sendEmailWrongInstagram(leadBean, instagram);
            notificationService.sendMessage("Пользователь при подтверждении ввел неверный инстаграм: " +
                    InstagramClient.accountURL(instagram) + " " + leadBean);
            return;
        }

        String oldInstagram = orderBean.getLead().getInstagram();
        if (!oldInstagram.equals(instagram)) {
            //Пользователь изменил instagram, нужно поменять его у лида
            leadBean.setInstagram(instagram);
            leadBean = leadService.save(leadBean);

            //Также нужно поменять у контакта
            contactDataService.saveContactData(leadBean.getContact(), ContactDataType.instagram.data(instagram));
            //Если пользователь до этого указал неверный инстаграм, то меняем на верный и все ок
            for (IOBean ioBean : instaOrderService.getByOrder(orderBean)) {
                ioBean.setInstagram(instagram);
                user.ifPresent(ioBean::setUser);
                instaOrderService.save(ioBean);
                notificationService.sendMessage("Пользователь изменил свой инстаграм с " + oldInstagram + " на " +
                        InstagramClient.accountURL(instagram)
                        + " Нужно остановить заказ:" + ioBean);
            }
        }

        String followersRate = InstaFameField.followersRate.fromForm(tildaForm);
        //В новом варианте скорость задается внутри строки заказа
        if (StringHelper.isNotEmpty(followersRate)) {
            instaOrderService.confirmSpeed(orderBean, IOFollowersSpeed.getValue(followersRate));
        }

        confirmOrder(orderBean, leadBean);

        //Возможно пользователь выполнял эту операцию с другого устройства нежели заказывал, тогда нужно добавить к контакту этот clientId
        if (StringHelper.isNotEmpty(tildaForm.getClientId())) {
            contactDataService.saveContactData(leadBean.getContact(), ContactDataType.googleClientId.data(tildaForm.getClientId()));
        }
    }

    /**
     * Переводим заказ в подтвержденное состояние
     */
    public void confirmOrder(OrderBean orderBean, LeadBean leadBean) {
        //Если заказ еще не начат выполняться
        if (orderBean.getStatus() == OrderStatus.Paid) {
            instaOrderService.confirmOrder(orderBean);

            orderBean.setStatus(OrderStatus.Confirmed);
            orderService.save(orderBean);
        }
        //Переводим заявку на следующий этап
        String leadStageCode = leadBean.getStage().getCode();
        if (InstaFameCrmService.INSTAGRAM_CONFIRM_SENT.equals(leadStageCode)
                || InstaFameCrmService.ORDER_PAYED.equals(leadStageCode)) {
            leadService.changeStage(leadBean, InstaFameCrmService.ORDER_START);
        }
    }

    private OrderBean getOrder(String orderId) {
        return orderService.repository().findByOrderId(orderId);
    }

    /**
     * Зарегистрироваться
     */
    private void registration(String requestId, TildaForm tildaForm) {
        String name = tildaForm.getNotNull("name");
        String email = tildaForm.getNotNull("email");
        try {
            accountController.registerAccount(name, email);
        } catch (EmailAlreadyUsedException e) {
            log.info("Account for email:" + email + " is already exists, can't register");
        }
    }

    private void resetPassword(String requestId, TildaForm tildaForm) {
        String email = tildaForm.getNotNull("email");
        accountController.requestPasswordReset(email);
    }

}
