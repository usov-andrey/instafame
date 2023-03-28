package com.xalap.wow.item.stats;

import com.xalap.vaadin.custom.tab.ListTab;
import com.xalap.wow.item.ItemBean;

import java.util.Comparator;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 26/11/2019
 */
public class ItemStatsListTab extends ListTab<ItemStatsBean, ItemBean> {

    public ItemStatsListTab(ItemStatsService service) {
        gridPanel.dataSource().setMemoryDataProvider(() -> {
            List<ItemStatsBean> byItem = service.repository().findByItem(getParentBean());
            byItem.sort(Comparator.comparing(o -> o.getAuctionFile().getDataTime()));
            return byItem;
        });
        gridPanel.createColumns();
        gridPanel.buttons().addWithReload("Пересчитать", () -> service.recount(getParentBean()));
    }
}
