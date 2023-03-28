/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.pipeline;

import com.xalap.crm.service.pipeline.PipelineBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import com.xalap.crm.ui.pipeline.stage.PipelineStageFrame;
import com.xalap.vaadin.custom.tab.ListDetailsTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
public class PipelineStageListTab extends ListDetailsTab<PipelineStageBean, PipelineBean> {

    public PipelineStageListTab(PipelineFrame pipelineFrame, ServiceRef<PipelineStageService> service) {
        super(pipelineFrame, service);
        gridPanel.dataSource().setMemoryDataProvider(() -> service.get().repository().findByPipeline(getParentBean()));
        gridPanel.columns().addLink("Название", PipelineStageBean::getName, PipelineStageBean::getId, PipelineStageFrame.class);
        addCreateButton(() -> {
            PipelineStageBean bean = new PipelineStageBean();
            bean.setPipeline(getParentBean());
            return bean;
        });
    }
}
