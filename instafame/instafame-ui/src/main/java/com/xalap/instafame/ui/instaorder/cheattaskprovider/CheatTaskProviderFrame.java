/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.cheattaskprovider;

import com.vaadin.flow.router.Route;
import com.xalap.instafame.service.cheat.provider.CheatTaskProviderBean;
import com.xalap.instafame.service.cheat.provider.CheatTaskProviderService;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = CheatTaskProviderListFrame.VIEW_NAME, layout = MainLayout.class)
public class CheatTaskProviderFrame extends RootEntityFrame<CheatTaskProviderBean, Integer> {

    @Autowired
    public CheatTaskProviderFrame(ServiceRef<CheatTaskProviderService> service) {
        super(service, CheatTaskProviderListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
        );
    }

    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
