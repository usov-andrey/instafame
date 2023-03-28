/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage.action;

import com.xalap.crm.service.lead.LeadBean;

/**
 * @author Усов Андрей
 * @since 14/06/2019
 */
public abstract class PipelineStageAction {

    abstract public void run(LeadBean leadBean);
}
