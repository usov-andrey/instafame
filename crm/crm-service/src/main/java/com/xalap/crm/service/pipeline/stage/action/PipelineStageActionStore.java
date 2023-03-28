/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage.action;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.action.ChangeLeadStatusActionBean;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
public enum PipelineStageActionStore {

    changeLeadStatus("Смена этапа", ChangeLeadStatusActionBean.class) {
        @Override
        public void run(LeadBean leadBean) {

        }
    };

    private final String caption;
    private final Class<? extends PipelineStageActionBean> beanClass;

    PipelineStageActionStore(String caption, Class<? extends PipelineStageActionBean> beanClass) {
        this.caption = caption;
        this.beanClass = beanClass;
    }

    public String getCaption() {
        return caption;
    }

    public Class<? extends PipelineStageActionBean> getBeanClass() {
        return beanClass;
    }

    abstract public void run(LeadBean leadBean);
}
