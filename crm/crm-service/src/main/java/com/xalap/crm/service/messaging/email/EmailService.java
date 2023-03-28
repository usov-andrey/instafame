/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.email;

import com.xalap.crm.service.contact.ContactWithDataBean;
import com.xalap.crm.service.integration.sendgrid.SendGridService;
import com.xalap.crm.service.integration.sendgrid.sendmail.SendGridSendMailRequest;
import com.xalap.crm.service.integration.sendgrid.templates.Template;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.MessageChannel;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.MessageType;
import com.xalap.crm.service.messaging.thread.MessageThreadProvider;
import com.xalap.crm.service.messaging.thread.MessageThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * Сервис работы с почтой
 *
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Service
public class EmailService {

    @Autowired
    private SendGridService sendGridService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageThreadService messageThreadService;

    public List<Template> getActiveTemplates() {
        return sendGridService.getTemplates();
    }

    public Template getTemplate(String templateCode) {
        List<Template> activeTemplates = getActiveTemplates();
        for (Template template : activeTemplates) {
            if (template.getId().equals(templateCode)) {
                return template;
            }
        }
        throw new IllegalStateException("Not found template by code:" + templateCode + " in templates:" + activeTemplates);
    }

    public MessageBean sendMessage(LeadBean leadBean, Template template, String instagram) {
        return sendMessage(leadBean, template, emailProcessor -> {
        }, instagram);
    }

    public MessageBean sendMessage(LeadBean leadBean, Template template, Consumer<EmailAttributes> emailProcessorConsumer,
                                   String instagram) {
        MessageThreadProvider messageThreadProvider = messageThreadService.messageThreadProvider(leadBean.getContact());
        return sendMessage(messageThreadProvider, template, leadBean.getEmail(), emailProcessorConsumer, instagram);
    }

    public MessageBean sendMessage(MessageThreadProvider messageThreadProvider, Template template, String contactEmail,
                                   Consumer<EmailAttributes> emailProcessorConsumer, String instagram) {
        ContactWithDataBean contactWithDataBean = messageThreadProvider.getContactWithData();
        String name = contactWithDataBean.getContact().getName();
        SendGridSendMailRequest mailRequest = sendGridService.createMail("InstaFame", "support@instafame.ru",
                contactEmail, name, template.getId());

        if (instagram != null) {
            mailRequest.fill("INSTAGRAM", instagram);
        }
        mailRequest.fill("NAME", name);
        //Заполняем другими ключевыми словами
        emailProcessorConsumer.accept(mailRequest);

        sendGridService.send(mailRequest);


        Date time = new Date();
        MessageBean messageBean = new MessageBean();
        messageBean.setInternalId(contactEmail + "_" + template.getId());
        messageBean.setContactData(contactEmail);
        messageBean.setThread(messageThreadProvider.getOrCreateBean(messageThreadService));
        messageBean.setMessageType(MessageType.outbound);
        messageBean.setChannel(MessageChannel.email);
        messageBean.setCreateTime(time);
        messageBean.setText("Отправлено письмо на почту:" + contactEmail + " по шаблону:" + template.getName());
        return messageService.save(messageBean);
    }
}
