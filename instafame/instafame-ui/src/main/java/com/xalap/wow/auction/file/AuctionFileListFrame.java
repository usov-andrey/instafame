package com.xalap.wow.auction.file;

import com.vaadin.flow.router.Route;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.wow.auction.AuctionService;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 15/11/2019
 */
@Route(value = AuctionFileListFrame.VIEW_NAME, layout = MainLayout.class)
public class AuctionFileListFrame extends RootEntityListFrame<AuctionFileBean> {
    public static final String VIEW_NAME = "auctionFileList";

    @Autowired
    public AuctionFileListFrame(ServiceRef<AuctionFileService> serviceRef, ServiceRef<AuctionService> auctionServiceRef) {
        super(serviceRef);
        GridColumns<AuctionFileBean> columns = gridPanel.columns();

        columns.add(AuctionFileBean.CREATE_TIME, AuctionFileBean.DATA_TIME, AuctionFileBean.REGION);
        columns.actions((actions, bean) -> {
            AuctionFileService service = serviceRef.get();
            if (service.getCurrentAuctionFile() == null || !service.getCurrentAuctionFile().equals(bean)) {
                actions.addWithReload("Сделать текущим", () -> {
                    service.setCurrentAuctionFile(bean);
                    auctionServiceRef.get().clearCache();
                });
            }
        });

        gridPanel.buttons().addWithReload("Обновить аукцион", () -> {
            serviceRef.get().updateAuctions();
        });
    }

}
