/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage.action;

import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.holder.OrderHolder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Entity()
@Table(name = PipelineStageActionBean.NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "A_TYPE")
public class PipelineStageActionBean implements IdHolder<Integer>, OrderHolder {

    public static final String NAME = "CRM$PIPELINESTAGEACTION";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PipelineStageBean stage;
    private long orderIndex;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PipelineStageActionConditionBean condition;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public PipelineStageBean getStage() {
        return stage;
    }

    public void setStage(PipelineStageBean stage) {
        this.stage = stage;
    }

    public PipelineStageActionConditionBean getCondition() {
        return condition;
    }

    public void setCondition(PipelineStageActionConditionBean condition) {
        this.condition = condition;
    }

    @Override
    public long getOrderIndex() {
        return orderIndex;
    }

    @Override
    public void setOrderIndex(long orderIndex) {
        this.orderIndex = orderIndex;
    }

    public PipelineStageActionStore action() {
        for (PipelineStageActionStore pipelineStageAction : PipelineStageActionStore.values()) {
            if (pipelineStageAction.getBeanClass().equals(getClass())) {
                return pipelineStageAction;
            }
        }
        throw new IllegalStateException("Not found action:" + getClass());
    }
}
