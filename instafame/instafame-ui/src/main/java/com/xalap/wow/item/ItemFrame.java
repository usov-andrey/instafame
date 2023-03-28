package com.xalap.wow.item;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.xalap.vaadin.custom.component.ButtonWithLog;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.wow.auction.AuctionService;
import com.xalap.wow.item.stats.ItemStatsListTab;
import com.xalap.wow.item.stats.ItemStatsService;
import com.xalap.wow.ui.PriceComponent;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
@Route(value = ItemListFrame.VIEW_NAME, layout = MainLayout.class)
public class ItemFrame extends RootEntityFrame<ItemBean, Integer> {

    @Autowired
    public ItemFrame(ServiceRef<ItemService> service, AuctionService auctionService, ItemStatsService itemStatsService) {
        super(service, ItemListFrame.class);
        CustomVerticalLayout beforeTabs = new CustomVerticalLayout();

        setViewContent(withTabs()
                .beforeTabs(beforeTabs)
                .addMainTab(service)
                .addTab("Аукцион", new ItemAuctionListTab(auctionService))
                .addTab("Статистика", new ItemStatsListTab(itemStatsService))
        );
        addListener(value -> {
            HorizontalLayout buyPrice = new HorizontalLayout();
            buyPrice.add(new Label("Цена покупки:"), new PriceComponent(value.getBuyPrice()));

            HorizontalLayout sellPrice = new HorizontalLayout();
            sellPrice.add(
                    new Label("Цена продажи:"), new PriceComponent(value.getSellPrice()));
            beforeTabs.add(new ItemComponent(value));
            beforeTabs.add(buyPrice);
            beforeTabs.add(sellPrice);
            beforeTabs.add(new ButtonWithLog("Обновить",
                    () -> service.get().reloadItem(getBean().getItemId()))
            );
        });

    }


    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
