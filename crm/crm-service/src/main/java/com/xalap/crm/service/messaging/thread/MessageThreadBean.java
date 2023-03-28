/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.thread;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 04/06/2019
 */
@Entity()
@Table(name = MessageThreadBean.NAME)
public class MessageThreadBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$MTHREAD";
    public static final String LAST_MESSAGE_TIME = "lastMessageTime";
    public static final String LAST_MESSAGE_TEXT = "lastMessageText";

    @Id
    /*
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)*/
    private Integer id;

    @Caption("Последнее сообщение")
    @FieldName(LAST_MESSAGE_TIME)
    private Date lastMessageTime;
    @Caption("Текст")
    @Column(length = 10000)
    @FieldName(LAST_MESSAGE_TEXT)
    private String lastMessageText;
    @OneToOne
    @MapsId
    private ContactBean contact;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
        setId(contact.getId());
    }

    @Override
    public String toString() {
        return "MessageThreadBean{" +
                "id=" + id +
                ", lastMessageTime=" + lastMessageTime +
                ", lastMessageText='" + lastMessageText + '\'' +
                ", contact=" + contact +
                '}';
    }
}
