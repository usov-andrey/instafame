package com.xalap.wow.professionspell;

import com.vaadin.flow.router.Route;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.wow.auction.AuctionService;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 14/08/2019
 */
@Route(value = SpellListFrame.VIEW_NAME, layout = MainLayout.class)
public class SpellFrame extends RootEntityFrame<SpellBean, Integer> {

    @Autowired
    public SpellFrame(ServiceRef<SpellService> service, AuctionService auctionService) {
        super(service, SpellListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
        );

    }

    @Override
    protected String getTitle() {
        return getBean().name();
    }
}