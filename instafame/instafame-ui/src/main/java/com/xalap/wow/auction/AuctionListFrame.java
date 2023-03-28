package com.xalap.wow.auction;

import com.vaadin.flow.router.Route;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Usov Andrey
 * @since 30.05.2022
 */
@Route(value = com.xalap.wow.auction.AuctionListFrame.VIEW_NAME, layout = MainLayout.class)
public class AuctionListFrame extends RootEntityListFrame<AuctionBean> {
    public static final String VIEW_NAME = "auctionList";

    @Autowired
    public AuctionListFrame(ServiceRef<AuctionService> serviceRef) {
        super(serviceRef, GridDefaultSorting.asc("id"));
        gridPanel.createColumns();
        gridPanel.buttons().addWithReload("Обновить аукцион", () -> {
            serviceRef.get().updateAuctions();
        });
    }

}
