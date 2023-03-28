/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage;

import com.xalap.crm.service.pipeline.PipelineBean;
import com.xalap.framework.domain.holder.IdNameHolder;
import com.xalap.framework.domain.holder.OrderHolder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Entity()
@Table(name = PipelineStageBean.NAME)
public class PipelineStageBean implements IdNameHolder<Integer>, OrderHolder {

    public static final String NAME = "CRM$PIPELINESTAGE";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @NotNull
    private String name;
    private String code;
    private long orderIndex;
    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PipelineBean pipeline;

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


    public PipelineBean getPipeline() {
        return pipeline;
    }

    public void setPipeline(PipelineBean pipeline) {
        this.pipeline = pipeline;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public long getOrderIndex() {
        return orderIndex;
    }

    @Override
    public void setOrderIndex(long orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PipelineStageBean stageBean = (PipelineStageBean) o;

        return id.equals(stageBean.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "PipelineStatusBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", orderIndex=" + orderIndex +
                ", pipeline=" + pipeline +
                '}';
    }
}
