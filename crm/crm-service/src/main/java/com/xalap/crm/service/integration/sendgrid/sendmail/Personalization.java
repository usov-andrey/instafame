
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.sendgrid.sendmail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "to",
        "dynamic_template_data",
        "subject"
})
public class Personalization {

    @JsonProperty("to")
    private List<SendGridEMailAddress> to = null;
    @JsonProperty("dynamic_template_data")
    private Map<String, String> dynamicTemplateData;
    @JsonProperty("subject")
    private String subject;

    @JsonProperty("to")
    public List<SendGridEMailAddress> getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(List<SendGridEMailAddress> to) {
        this.to = to;
    }

    @JsonProperty("dynamic_template_data")
    public Map<String, String> getDynamicTemplateData() {
        return dynamicTemplateData;
    }

    @JsonProperty("dynamic_template_data")
    public void setDynamicTemplateData(Map<String, String> dynamicTemplateData) {
        this.dynamicTemplateData = dynamicTemplateData;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Personalization{" +
                "to=" + to +
                ", dynamicTemplateData=" + dynamicTemplateData +
                ", subject='" + subject + '\'' +
                '}';
    }
}
