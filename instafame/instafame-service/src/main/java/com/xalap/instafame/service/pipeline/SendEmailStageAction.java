/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.pipeline;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.messaging.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class SendEmailStageAction extends AbstractLeadStageAction {

    @Autowired
    private EmailService emailService;

    private String templateId;

    public SendEmailStageAction() {
    }

    public SendEmailStageAction(LeadBean leadBean, String templateId) {
        super(leadBean);
        this.templateId = templateId;
    }

    @Override
    protected void run(LeadBean leadBean) {
        emailService.sendMessage(leadBean, emailService.getTemplate(templateId), emailProcessor -> {
        }, leadBean.getInstagram());
    }
}
