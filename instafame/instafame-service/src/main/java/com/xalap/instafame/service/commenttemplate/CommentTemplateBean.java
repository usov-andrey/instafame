/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.commenttemplate;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 08/05/2019
 */
@Entity(name = CommentTemplateBean.NAME)
public class CommentTemplateBean implements IdHolder<Integer> {
    public static final String NAME = "IS$CommentTemplate";
    public static final String TEXT = "text";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;

    @Column(length = 5000)
    @FieldName(TEXT)
    private String comText;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getComText() {
        return comText;
    }

    public void setComText(String comText) {
        this.comText = comText;
    }
}
