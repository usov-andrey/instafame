/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.crm;

import com.xalap.crm.service.scheduler.SchedulerTask;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Получа
 *
 * @author Усов Андрей
 * @since 14/09/2019
 */
public class CrmPipelineSchedulerTask extends SchedulerTask {

    @Autowired
    private InstaFameCrmService crmService;

    @Override
    protected void doRun() {
        crmService.processPipeline();
    }
}
