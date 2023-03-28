package com.xalap.wow.auction.file;

import com.vaadin.flow.router.Route;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
@Route(value = AuctionFileListFrame.VIEW_NAME, layout = MainLayout.class)
public class AuctionFileFrame extends RootEntityFrame<AuctionFileBean, Integer> {

    @Autowired
    public AuctionFileFrame(ServiceRef<AuctionFileService> service) {
        super(service, AuctionFileListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
        );
    }


    @Override
    protected String getTitle() {
        return getBean().getFileName();
    }
}
