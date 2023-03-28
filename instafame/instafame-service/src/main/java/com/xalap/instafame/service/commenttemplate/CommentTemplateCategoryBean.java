/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.commenttemplate;

import com.xalap.framework.domain.holder.IdNameHolder;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 08/05/2019
 */
@Entity(name = CommentTemplateCategoryBean.NAME)
public class CommentTemplateCategoryBean implements IdNameHolder<Integer> {

    public static final String NAME = "IS$CommentTemplateCategory";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
