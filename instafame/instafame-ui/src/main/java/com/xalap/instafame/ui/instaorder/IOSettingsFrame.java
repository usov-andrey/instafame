/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder;

import com.vaadin.flow.router.Route;
import com.xalap.instafame.service.instaorder.settings.InstaOrderSettingsBean;
import com.xalap.instafame.service.instaorder.settings.InstaOrderSettingsService;
import com.xalap.vaadin.custom.frame.RootBeanFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Экран настроек
 *
 * @author Усов Андрей
 * @since 10/07/2019
 */
@Route(value = IOSettingsFrame.VIEW_NAME, layout = MainLayout.class)
public class IOSettingsFrame extends RootBeanFrame<InstaOrderSettingsBean> {

    public static final String VIEW_NAME = "InstaOrderSettings";

    @Autowired
    public IOSettingsFrame(ServiceRef<InstaOrderSettingsService> service) {
        setViewContent(withTabs()
                .addMainTab(service));
        //Для того, чтобы отображались все лукап поля, нужно внести их в InstaFameUI
        setBean(service.get().bean());
    }

    @Override
    protected String getTitle() {
        return "Настройки";
    }


}
