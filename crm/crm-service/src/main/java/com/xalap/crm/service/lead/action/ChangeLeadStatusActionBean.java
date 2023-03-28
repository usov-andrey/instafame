/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.lead.action;

import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.action.PipelineStageActionBean;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Entity
@DiscriminatorValue("ChangeLeadStatus")
public class ChangeLeadStatusActionBean extends PipelineStageActionBean {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PipelineStageBean toStatus;

    public PipelineStageBean getToStatus() {
        return toStatus;
    }

    public void setToStatus(PipelineStageBean toStatus) {
        this.toStatus = toStatus;
    }
}
