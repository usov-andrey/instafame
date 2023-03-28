/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage.action;

import com.xalap.framework.domain.holder.IdHolder;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Entity()
@Table(name = PipelineStageActionConditionBean.NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "RSC_TYPE")
public class PipelineStageActionConditionBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$PIPELINESACONDITION";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

}
