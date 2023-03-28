/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging;

import com.xalap.crm.service.messaging.thread.MessageThreadBean;
import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Сообщение контакту, входящее и исходящее
 *
 * @author Усов Андрей
 * @since 28/05/2019
 */
@Entity()
@Table(name = MessageBean.NAME)
public class MessageBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$MMESSAGE";
    public static final String CREATE_TIME = "createTime";
    public static final String MESSAGE_TYPE = "messageType";
    public static final String CHANNEL = "channel";
    public static final String TEXT = "text";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;

    @Caption("Время создания")
    @FieldName(CREATE_TIME)
    private Date createTime;
    @FieldName(MESSAGE_TYPE)
    private MessageType messageType;
    @FieldName(CHANNEL)
    private MessageChannel channel;
    @Caption("Текст")
    @Column(length = 10000)
    @FieldName(TEXT)
    private String text;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MessageThreadBean thread;
    /**
     * id сообщения из внешней системы отправки
     * В случае, если сообщение не отправлено или добавлено в очередь для отправки, то здесь будет какое-то константное значение
     */
    @NotNull
    private String internalId;
    private String contactData;//Адрес, куда было отправлено сообщение

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageThreadBean getThread() {
        return thread;
    }

    public void setThread(MessageThreadBean thread) {
        this.thread = thread;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getContactData() {
        return contactData;
    }

    public void setContactData(String contactData) {
        this.contactData = contactData;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", messageType=" + messageType +
                ", channel=" + channel +
                ", text='" + text + '\'' +
                ", thread=" + thread +
                '}';
    }

    public void save(MessageService messageService) {
        messageService.save(this);
    }
}
