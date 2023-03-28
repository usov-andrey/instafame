/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.sendgrid;

import com.xalap.crm.service.integration.sendgrid.sendmail.*;
import com.xalap.crm.service.integration.sendgrid.templates.Template;
import com.xalap.crm.service.integration.sendgrid.templates.TemplatesResponse;
import com.xalap.framework.exception.IORuntimeException;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.HttpClientUtils;
import com.xalap.framework.utils.StringHelper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 05/02/2019
 */
@Service
public class SendGridService {

    private static final Logger log = LoggerFactory.getLogger(SendGridService.class);

    private static final String URL = "https://api.sendgrid.com/v3";
    private static final String MAIL_SEND_URL = URL + "/mail/send";
    private static final String TEMPLATES_URL = URL + "/templates?generations=dynamic";

    @Value("${sendgrid.api-key:}")
    private String apiKey;

    private boolean testMode;

    private final JsonService jsonService;

    public SendGridService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    /**
     * Шаблоны используемые в SendGrid
     */
    public List<Template> getTemplates() {
        Request http = auth(Request.Get(TEMPLATES_URL));
        String response = HttpClientUtils.execDefaultStringResponse(http);
        log.debug("Response:" + response);
        TemplatesResponse templatesResponse = jsonService.getJson(response, TemplatesResponse.class);
        return templatesResponse.getTemplates();
    }

    public SendGridSendMailRequest createMail(String fromName, String fromEmail, String toEmail, String toName, String templateId) {
        if (StringHelper.isEmpty(toEmail)) {
            throw new IllegalArgumentException("toEmail is empty");
        }
        SendGridSendMailRequest mailRequest = new SendGridSendMailRequest();

        SendGridEMailAddress from = new SendGridEMailAddress(fromEmail, fromName);
        mailRequest.setFrom(from);
        mailRequest.setReplyTo(from);

        List<Personalization> personalizations = new ArrayList<>();
        Personalization personalization = new Personalization();
        personalization.setDynamicTemplateData(new HashMap<>());
        personalizations.add(personalization);
        mailRequest.setPersonalizations(personalizations);

        List<SendGridEMailAddress> toList = new ArrayList<>();
        toList.add(new SendGridEMailAddress(toEmail, toName));
        personalization.setTo(toList);
        mailRequest.setTemplateId(templateId);

        return mailRequest;
    }

    public void send(SendGridSendMailRequest mailRequest) {
        if (testMode) {
            log.info("TEST: Send email request:" + mailRequest);
            return;
        }
        log.debug("Send email request:" + mailRequest);
        try {
            HttpClientUtils.execDefaultWithPayload(auth(Request.Post(MAIL_SEND_URL)), ContentType.APPLICATION_JSON,
                    jsonService.getString(mailRequest));
        } catch (IOException e) {
            throw new IORuntimeException("Error on sendGrid request:" + mailRequest, e);
        }
    }

    private Request auth(Request http) {
        http.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
        http.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        return http;
    }

    public void setUtm(SendGridSendMailRequest mail, String utmCampaign, String utmContent) {
        TrackingSettings trackingSettings = new TrackingSettings();
        GoogleAnalyticsSetting googleAnalyticsSetting = new GoogleAnalyticsSetting();
        googleAnalyticsSetting.setEnable(true);
        googleAnalyticsSetting.setCampaignMedium("email");
        googleAnalyticsSetting.setCampaignName(utmCampaign);
        googleAnalyticsSetting.setCampaignContent(utmContent);
        trackingSettings.setGoogleAnalyticsSetting(googleAnalyticsSetting);
        mail.setTrackingSettings(trackingSettings);
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}
