/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.lead;

import com.xalap.crm.service.contact.ContactWithDataBean;

/**
 * @author Усов Андрей
 * @since 15/06/2019
 */
public class LeadWithContactData {

    private final LeadBean leadBean;
    private final ContactWithDataBean contactWithDataBean;

    public LeadWithContactData(LeadBean leadBean, ContactWithDataBean contactWithDataBean) {
        this.leadBean = leadBean;
        this.contactWithDataBean = contactWithDataBean;
    }

    public LeadBean getLeadBean() {
        return leadBean;
    }

    public ContactWithDataBean getContactWithDataBean() {
        return contactWithDataBean;
    }
}
