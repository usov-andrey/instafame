/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.cost.CostBean;
import com.xalap.crm.service.cost.CostService;
import com.xalap.framework.utils.DateUtils;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
@Route(value = CostListFrame.VIEW_NAME, layout = MainLayout.class)
public class CostFrame extends RootEntityFrame<CostBean, Integer> {

    @Autowired
    public CostFrame(ServiceRef<CostService> service) {
        super(service, CostListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
        );
    }

    @Override
    protected String getTitle() {
        return "Затраты за " + DateUtils.formatWithRu(getBean().getCostTime()) + ": " + getBean().getCost();
    }
}
