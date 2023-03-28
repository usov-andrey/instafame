/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.messaging.thread.MessageThreadBean;
import com.xalap.crm.service.messaging.thread.MessageThreadService;
import com.xalap.framework.data.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 29/05/2019
 */
@Service
public class MessageService extends CrudService<MessageBean, MessageRepository, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageThreadService threadService;

    public MessageService() {
        super();
    }

    @Override
    @Transactional
    public MessageBean save(MessageBean bean) {
        MessageBean newBean = super.save(bean);
        MessageThreadBean thread = newBean.getThread();
        thread.setLastMessageTime(newBean.getCreateTime());
        thread.setLastMessageText(newBean.getText());
        threadService.save(thread);
        return newBean;
    }

    /**
     * @return Список сообщений по каналу
     */
    public List<MessageBean> getMessageList(ContactBean contactBean, MessageChannel messageChannel) {
        Optional<MessageThreadBean> thread = threadService.getThread(contactBean);
        if (thread.isPresent()) {
            return repository().findByChannelAndThread(messageChannel, thread.get());
        }
        return new ArrayList<>();
    }

    private void sortMessagesByCreateTime(List<MessageBean> messageBeanList) {
        messageBeanList.sort(Comparator.comparing(MessageBean::getCreateTime));
    }

    /**
     * Сообщения отсортированые по времени создания
     */
    public List<MessageBean> getMessagesSorted(MessageThreadBean threadBean) {
        List<MessageBean> byThread = getMessages(threadBean);
        sortMessagesByCreateTime(byThread);
        return byThread;
    }

    public List<MessageBean> getMessages(MessageThreadBean threadBean) {
        return getRepository().findByThread(threadBean);
    }

    public List<MessageBean> getMessages(MessageChannel channel, MessageThreadBean threadBean) {
        return getRepository().findByChannelAndThread(channel, threadBean);
    }

    /**
     * Данный метод никогда не выбрасывает исключение, поэтому его можно вызывать в catch блоке
     */
    public void errorSend(Integer messageId) {
        try {
            MessageBean messageBean = get(messageId);
            messageBean.setMessageType(MessageType.error_on_sending);
            messageBean.setInternalId(messageBean.getMessageType().name());
            messageBean.save(this);
        } catch (Exception e) {
            log.error("Error on change message status, messageId:" + messageId, e);
        }
    }
}
