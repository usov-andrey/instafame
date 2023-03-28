package com.xalap.wow.item;

import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.tab.ListTab;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.auction.AuctionService;
import com.xalap.wow.ui.PriceComponent;

import java.util.Collection;
import java.util.Comparator;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
public class ItemAuctionListTab extends ListTab<Auction, ItemBean> {

    public ItemAuctionListTab(AuctionService service) {
        gridPanel.dataSource().setMemoryDataProvider(new SerializableSupplier<Collection<Auction>>() {
            @Override
            public Collection<Auction> get() {
                return service.getAuction().getAuctions(getParentBean());
            }
        });
        GridColumns<Auction> columns = gridPanel.columns();
        columns.add("Продавец", new ValueProvider<Auction, String>() {
            @Override
            public String apply(Auction auction) {
                return auction.getOwner();
            }
        }).add("Количество", auction -> Integer.toString(auction.getQuantity()));
        columns.addComponent("Ставка", auction -> new PriceComponent(auction.getBid())).setComparator(new Comparator<Auction>() {
            @Override
            public int compare(Auction o1, Auction o2) {
                if (o1 == null) {
                    return -1;
                } else if (o2 == null) {
                    return 1;
                }
                return o1.getBid().compareTo(o2.getBid());
            }
        });
        columns.add("Длительность", Auction::getTimeLeft);
        columns.addComponent("Цена выкупа", auction -> new PriceComponent(auction.getBuyout())).setComparator(comparatorBuyout());
        columns.addComponent("Цена выкупа за одну", auction -> new PriceComponent(auction.getBuyout() / auction.getQuantity()))
                .setComparator(comparatorBuyout());
    }

    private Comparator<Auction> comparatorBuyout() {
        return new Comparator<Auction>() {
            @Override
            public int compare(Auction o1, Auction o2) {
                if (o1 == null) {
                    return -1;
                } else if (o2 == null) {
                    return 1;
                }
                return o1.getBuyout().compareTo(o2.getBuyout());
            }
        };
    }
}
