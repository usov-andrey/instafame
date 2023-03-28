/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.pipeline;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.pipeline.PipelineBean;
import com.xalap.crm.service.pipeline.PipelineService;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = PipelineListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Воронки")
public class PipelineListFrame extends RootEntityListFrame<PipelineBean> {

    public static final String VIEW_NAME = "pipelines";

    @Autowired
    public PipelineListFrame(ServiceRef<PipelineService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));
        gridPanel.columns().addLink("Название", PipelineBean::getName, PipelineBean::getId, PipelineFrame.class);

        addCreateButton();
    }
}
