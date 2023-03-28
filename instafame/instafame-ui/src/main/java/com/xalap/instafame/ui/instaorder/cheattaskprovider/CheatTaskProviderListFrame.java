/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.cheattaskprovider;

import com.vaadin.flow.router.Route;
import com.xalap.instafame.service.cheat.provider.CheatTaskProviderBean;
import com.xalap.instafame.service.cheat.provider.CheatTaskProviderService;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.instafame.service.cheat.provider.CheatTaskProviderBean.ACTIVE;
import static com.xalap.instafame.service.cheat.provider.CheatTaskProviderBean.COLUMN_ID;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = CheatTaskProviderListFrame.VIEW_NAME, layout = MainLayout.class)
public class CheatTaskProviderListFrame extends RootEntityListFrame<CheatTaskProviderBean> {

    public static final String VIEW_NAME = "cheatTaskProviders";

    @Autowired
    public CheatTaskProviderListFrame(ServiceRef<CheatTaskProviderService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));
        gridPanel.columns().addLink("Название", CheatTaskProviderBean::getName, CheatTaskProviderBean::getId, CheatTaskProviderFrame.class);
        gridPanel.columns().add(ACTIVE);

        addCreateButton();
    }
}
