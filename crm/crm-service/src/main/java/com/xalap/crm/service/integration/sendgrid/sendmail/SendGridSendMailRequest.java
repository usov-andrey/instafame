/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.sendgrid.sendmail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.crm.service.messaging.email.EmailAttributes;
import com.xalap.framework.utils.StringHelper;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "personalizations",
        "from",
        "reply_to",
        "template_id"
})
public class SendGridSendMailRequest implements EmailAttributes {

    @JsonProperty("personalizations")
    private List<Personalization> personalizations = null;
    @JsonProperty("from")
    private SendGridEMailAddress from;
    @JsonProperty("reply_to")
    private SendGridEMailAddress replyTo;
    @JsonProperty("template_id")
    private String templateId;
    /**
     * The email's tracking settings.
     */
    @JsonProperty("tracking_settings")
    private TrackingSettings trackingSettings;

    @JsonProperty("personalizations")
    public List<Personalization> getPersonalizations() {
        return personalizations;
    }

    @JsonProperty("personalizations")
    public void setPersonalizations(List<Personalization> personalizations) {
        this.personalizations = personalizations;
    }

    @JsonProperty("from")
    public SendGridEMailAddress getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(SendGridEMailAddress from) {
        this.from = from;
    }

    @JsonProperty("reply_to")
    public SendGridEMailAddress getReplyTo() {
        return replyTo;
    }

    @JsonProperty("reply_to")
    public void setReplyTo(SendGridEMailAddress replyTo) {
        this.replyTo = replyTo;
    }

    @JsonProperty("template_id")
    public String getTemplateId() {
        return templateId;
    }

    @JsonProperty("template_id")
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public TrackingSettings getTrackingSettings() {
        return trackingSettings;
    }

    public void setTrackingSettings(TrackingSettings trackingSettings) {
        this.trackingSettings = trackingSettings;
    }

    @Override
    public String toString() {
        return "SendGridSendMailRequest{" +
                "personalizations=" + personalizations +
                ", from=" + from +
                ", replyTo=" + replyTo +
                ", templateId='" + templateId + '\'' +
                '}';
    }

    public void fill(String fieldName, String value) {
        if (StringHelper.isNotEmpty(value)) {
            getPersonalizations().get(0).getDynamicTemplateData().put(fieldName, value);
        }
    }

}
