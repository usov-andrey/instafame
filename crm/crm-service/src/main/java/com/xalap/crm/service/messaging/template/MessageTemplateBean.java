/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.template;

import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 28/05/2019
 */
@Entity()
@Table(name = MessageTemplateBean.NAME)
public class MessageTemplateBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$MMESSAGETEMPLATE";
    public static final String TEXT = "text";
    public static final String NAME1 = "name";
    public static final String CODE = "code";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;

    @FieldName(NAME1)
    private String name;
    @Caption("Текст")
    @Column(length = 10000)
    @FieldName(TEXT)
    private String text;
    @FieldName(CODE)
    private String code;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MessageTemplateBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
