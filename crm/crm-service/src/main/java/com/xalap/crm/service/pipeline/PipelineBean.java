/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline;

import com.xalap.framework.domain.holder.IdNameHolder;
import com.xalap.framework.domain.holder.OrderHolder;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Entity()
@Table(name = PipelineBean.NAME)
public class PipelineBean implements IdNameHolder<Integer>, OrderHolder {

    public static final String NAME = "CRM$PIPELINE";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    private String name;
    private String code;
    private long orderIndex;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getOrderIndex() {
        return orderIndex;
    }

    @Override
    public void setOrderIndex(long orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PipelineBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orderIndex=" + orderIndex +
                '}';
    }
}
