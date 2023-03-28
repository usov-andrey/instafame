/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.direct.service;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.MessageChannel;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.MessageType;
import com.xalap.crm.service.messaging.thread.MessageThreadProvider;
import com.xalap.crm.service.messaging.thread.MessageThreadService;
import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.notification.NotificationService;
import com.xalap.instagram.direct.service.settings.InstagramDirectSettingsService;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Функции по работе с instagram direct
 * После переноса mgp в отдельный модуль эта функциональность не чинилась,
 * так как ее использование через mgp очень часто приводит к блокировке.
 *
 * @author Усов Андрей
 * @since 31/05/2019
 */
@Service
@AllArgsConstructor
public class InstagramDirectServiceImpl implements com.xalap.crm.service.messaging.direct.InstagramDirectService {

    private static final Logger log = LoggerFactory.getLogger(InstagramDirectServiceImpl.class);

    private final JsonService converterService;
    private final MessageService messageService;
    private final MessageThreadService messageThreadService;
    private final InstagramUserService userService;
    private final SchedulerService schedulerService;
    private final NotificationService notificationService;
    private final InstagramDirectSettingsService directSettingsService;

    @org.springframework.context.event.EventListener({ContextRefreshedEvent.class})
    @Override
    public void init() {
        schedulerService.register(UpdateInstagramMessagesTask.class, "Обновление сообщения в инстаграм");
    }

    @Async
    public void asyncUpdateAndAccept() {
        try {
            //updateAllPendingThreads();
            updateAllMessages();
        } catch (ServiceTemporaryException e) {
            e.log(log, "Instagram connection error on update direct messages");
        }
    }

    /**
     * Отправка шаблонного сообщения
     */
    public void sendTemplate(ContactBean contactBean, String instagram, String text) {
        sendMessage(messageThreadService.messageThreadProvider(contactBean), instagram, text);
    }

    /**
     * Отправка текста сообщения
     */
    public MessageBean sendMessage(Integer contactId, String instagram, String text) {
        return sendMessage(messageThreadService.messageThreadProvider(contactId), instagram, text);
    }

    /**
     * Обновляет сообщения по определенному пользователю
     */
    public void updateThreadMessages(String instagram) throws ServiceTemporaryException {
        /*
        InstagramUserBean userBean = getUser(instagram);
        DirectThread thread = instagramClient.getThreadByUser(userBean);
        if (thread != null) {
            DirectThreadUpdater updater = new DirectThreadUpdater(this, messageService, messageThreadService, thread);
            updater.update();
        }*/
    }

    public void sendMessage(LeadBean leadBean, String text) {
        MessageThreadProvider messageThreadProvider = messageThreadService.messageThreadProvider(leadBean.getContact());
        sendMessage(messageThreadProvider, leadBean.getInstagram(), text);
    }

    /**
     * Обновить все сообщения
     */
    public void updateAllMessages() throws ServiceTemporaryException {
        //Чтобы не получать из инстаграм информацию о всех диалогах, передаем туда threadId уже полученного
        /*
        String lastActivityAt = directSettingsService.getLastActivityAt();
        List<DirectThread> threads = new ArrayList<>();
        updateThreads(lastActivityAt, threads::add);
        //Вначале обрабатываем самые старые темы
        Collections.reverse(threads);
        for (DirectThread thread : threads) {
            DirectThreadUpdater updater = new DirectThreadUpdater(this, messageService, messageThreadService, thread);
            updater.update();
            //Сохраняем информацию о том, что мы прочитали Thread
            directSettingsService.update(bean -> bean.setLastActivityAt(thread.getLastActivityAt()));
        }*/
    }

    /**
     * Отправляем сообщение
     */
    private MessageBean sendMessage(MessageThreadProvider messageThreadProvider, String instagram, String text) {
        InstagramUserBean user = getUser(instagram);
        boolean isAutoSending = directSettingsService.get().isAllowToSend();
        MessageBean messageBean = new MessageBean();
        messageBean.setThread(messageThreadProvider.getOrCreateBean(messageThreadService));
        messageBean.setMessageType(isAutoSending ? MessageType.sending : MessageType.manual_sending);
        messageBean.setInternalId(messageBean.getMessageType().name());
        messageBean.setContactData(instagram);
        messageBean.setChannel(MessageChannel.instagram);
        messageBean.setCreateTime(new Date());
        messageBean.setText(text);
        messageBean = messageService.save(messageBean);

        if (isAutoSending) {
            sendMessage(user, text, messageBean.getId());
        } else {
            notificationService.sendMessage("Добавление в очередь сообщение к " + user.accountUrl() + ": " + text);
        }
        return messageBean;
    }

    public void sendMessage(InstagramUserBean user, String text, Integer messageId) {

    }

    /**
     * @return Пользователя по instagram или исключение в случае, если не найден
     */
    private InstagramUserBean getUser(String instagram) {
        Optional<InstagramUserBean> user = userService.getOrCreateUser(instagram);
        if (user.isEmpty()) {
            throw new IllegalStateException("Not found instagram user by " + instagram);
        }
        return user.get();
    }

    private <T> T getResult(String s, Class<T> resultClass) {
        if (s.contains("Something went wrong")) {
            throw new IllegalStateException(s);
        }
        return converterService.getJson(s, resultClass);
    }

    public void updateAllPendingThreads() {

    }
}
