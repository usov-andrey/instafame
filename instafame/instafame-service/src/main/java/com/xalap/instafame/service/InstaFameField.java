/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service;

import com.xalap.crm.service.integration.mailchimp.member.MailChimpMember;
import com.xalap.crm.service.integration.sendgrid.sendmail.SendGridSendMailRequest;
import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.web.request.HttpForm;
import com.xalap.instagram.service.api.InstagramClient;

/**
 * @author Усов Андрей
 * @since 28/01/2019
 */
public enum InstaFameField {

    name("NAME") {
    },
    instagram("INSTAGRAM", true) {
        @Override
        public String fromForm(HttpForm form) {
            String value = form.get("INSTAGRAM_O");//В форме отправки заказа это поле называется по-другому
            if (StringHelper.isEmpty(value)) {
                value = form.get("instagramName");
            }
            if (StringHelper.isEmpty(value)) {
                value = super.fromForm(form);
            }
            if (StringHelper.isEmpty(value)) {
                return "";
            }
            value = InstagramClient.correctUserName(value).replace("#", "");
            return value;
        }

    },
    clientId("CLIENTID") {
    },
    email("EMAIL", true) {

        @Override
        public String fromForm(HttpForm form) {
            String value = super.fromForm(form);
            if (value == null) {//Иногда к нам приходит поле e-mail в нижнем регистре и мы не можем изменть, чтобы они присылали по-другому
                value = fromForm(form, getFormFieldName().toLowerCase());
            }
            return value;
        }
    },
    status("STATUS"),
    formStatus("FORMSTATUS"),//передача статуса о переходе сделки в новый статус через форму
    followersRate("FOLLOWERSRATE") {
    },
    paymentId("PAYMENTID") {
    },
    utmSource("UTM_SOURCE") {

        @Override
        protected String getFormFieldName() {
            return super.getFormFieldName().toLowerCase();
        }
    },
    utmMedium("UTM_MEDIUM") {

        @Override
        protected String getFormFieldName() {
            return super.getFormFieldName().toLowerCase();
        }
    },
    utmCampaign("UTM_CAMPAIGN") {
        @Override
        protected String getFormFieldName() {
            return super.getFormFieldName().toLowerCase();
        }
    },
    utmContent("UTM_CONTENT") {

        @Override
        protected String getFormFieldName() {
            return super.getFormFieldName().toLowerCase();
        }
    },
    utmTerm("UTM_TERM") {

        @Override
        protected String getFormFieldName() {
            return super.getFormFieldName().toLowerCase();
        }
    }, followersPackage("FOLLOWERS_PACKAGE"),
    followers("FOLLOWERS"), likes("LIKES"), comments("COMMENTS"),
    sex("SEX"), price1000("PRICE1000"), experience("EXPERIENCE"), giftType("GIFTTYPE"), strategy("STRATEGY");

    private final String fieldName;
    private boolean isLowerCase = false;

    InstaFameField(String name) {
        this.fieldName = name;
    }

    InstaFameField(String fieldName, boolean isLowerCase) {
        this.fieldName = fieldName;
        this.isLowerCase = isLowerCase;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String fromForm(HttpForm form) {
        return fromForm(form, getFormFieldName());
    }

    protected String fromForm(HttpForm form, String formFieldName) {
        String value = form.get(formFieldName);
        if (value != null && isLowerCase) {
            value = value.toLowerCase();
        }
        return value;
    }

    protected String getFormFieldName() {
        return fieldName;
    }

    public void fillMember(MailChimpMember member, String value) {
        member.getMergeFields().put(getFieldName(), value);
    }

    public void fillMail(SendGridSendMailRequest mail, String value) {
        mail.getPersonalizations().get(0).getDynamicTemplateData().put(getFieldName(), value);
    }

}
