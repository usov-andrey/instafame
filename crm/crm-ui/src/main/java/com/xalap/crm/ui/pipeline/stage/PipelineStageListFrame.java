/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.pipeline.stage;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Route(value = PipelineStageListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Этапы воронок")
public class PipelineStageListFrame extends RootEntityListFrame<PipelineStageBean> {

    public static final String VIEW_NAME = "pipelineStagesList";

    @Autowired
    public PipelineStageListFrame(ServiceRef<PipelineStageService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));
        GridColumns<PipelineStageBean> columns = gridPanel.columns();

        columns.add("Воронка", stageBean -> stageBean.getPipeline().getName());
        columns.addLink("Этап", PipelineStageBean::getName, PipelineStageBean::getId, PipelineStageFrame.class);
        columns.add("Код", PipelineStageBean::getCode);

        addCreateButton();
    }
}
