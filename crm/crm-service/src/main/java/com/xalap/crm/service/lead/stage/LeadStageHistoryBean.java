/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.lead.stage;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.framework.domain.holder.IdHolder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Entity()
@Table(name = LeadStageHistoryBean.NAME)
public class LeadStageHistoryBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$LEADSTAGEHISTORY";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private @NotNull PipelineStageBean stage;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private @NotNull LeadBean lead;
    @NotNull
    private Date startTime;
    private Date endTime;

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

    public LeadBean getLead() {
        return lead;
    }

    public void setLead(LeadBean lead) {
        this.lead = lead;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "LeadStageHistoryBean{" +
                "id=" + id +
                ", stage=" + stage +
                ", lead=" + lead +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
