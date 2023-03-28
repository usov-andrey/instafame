/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact.event;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Entity()
@Table(name = ContactEventBean.NAME)
public class ContactEventBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$CONTACT_EVENT";
    public static final String EVENT_TIME = "eventTime";
    public static final String EVENT_TYPE = "eventType";
    public static final String TEXT_FIELD_NAME = "text";
    public static final String CLIENT_ID = "clientId";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @FieldName(EVENT_TIME)
    private Date eventTime;
    @FieldName(EVENT_TYPE)
    private ContactEventType eventType;
    @Column(length = 5000)
    @FieldName(TEXT_FIELD_NAME)
    private String text;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ContactBean contact;
    @FieldName(CLIENT_ID)
    private String clientId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public ContactEventType getEventType() {
        return eventType;
    }

    public void setEventType(ContactEventType eventType) {
        this.eventType = eventType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
