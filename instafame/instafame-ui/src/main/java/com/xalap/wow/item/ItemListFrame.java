package com.xalap.wow.item;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.wow.item.stats.ItemStatsService;
import com.xalap.wow.ui.PriceComponent;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.BiPredicate;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
@Route(value = ItemListFrame.VIEW_NAME, layout = MainLayout.class)
public class ItemListFrame extends RootEntityListFrame<ItemBean> {
    public static final String VIEW_NAME = "itemList";

    @Autowired
    public ItemListFrame(ServiceRef<ItemService> service, ItemStatsService statsService) {
        super(service);
        gridPanel.filters().addText("Поиск", new BiPredicate<ItemBean, String>() {
            @Override
            public boolean test(ItemBean bean, String s) {
                return s.isEmpty() || bean.getName().toLowerCase().contains(s.toLowerCase());
            }
        });
        gridPanel.filters().addText("ItemId", new BiPredicate<ItemBean, String>() {
            @Override
            public boolean test(ItemBean bean, String s) {
                return s.isEmpty() || bean.getItemId() == Long.parseLong(s);
            }
        });
        GridColumns<ItemBean> columns = gridPanel.columns();
        columns.addComponent("Название", new ValueProvider<ItemBean, Component>() {
            @Override
            public Component apply(ItemBean itemBean) {
                return new ItemComponent(itemBean);
            }
        });
        columns.addComponent("Цена продажи", bean -> new PriceComponent(bean.getSellPrice()));
        columns.addComponent("Цена покупки", bean -> new PriceComponent(bean.getBuyPrice()));
        gridPanel.buttons().addWithReload("Пересчитать статистику", new Runnable() {
            @Override
            public void run() {
                statsService.recount(gridPanel.getItems());
            }
        });
    }

}
