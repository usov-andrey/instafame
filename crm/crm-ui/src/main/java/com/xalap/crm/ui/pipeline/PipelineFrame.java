/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.pipeline;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.pipeline.PipelineBean;
import com.xalap.crm.service.pipeline.PipelineService;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = PipelineListFrame.VIEW_NAME, layout = MainLayout.class)
public class PipelineFrame extends RootEntityFrame<PipelineBean, Integer> {

    @Autowired
    public PipelineFrame(ServiceRef<PipelineService> service, ServiceRef<PipelineStageService> statusService) {
        super(service, PipelineListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
                .addTab("Этапы", new PipelineStageListTab(this, statusService)
                ));
    }

    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
