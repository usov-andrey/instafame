package com.xalap.wow.auction;

import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.IOHelper;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.api.auction.AuctionsResponse;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.item.ItemWithQuantity;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Усов Андрей
 * @since 16/08/2019
 */
public class AuctionDataOld {

    private final Map<Long, List<Auction>> items = new HashMap<>();

    public AuctionDataOld readFile(JsonService jsonService, File file) {
        for (Auction auction : readFromFile(jsonService, file).getAuctions()) {
            Long item = auction.getItem();
            if (auction.getBuyout() != null) {//Смотрим только аукционы с buyout
                CollectionHelper.getArrayListOrCreate(items, item).add(auction);
            }
        }
        return this;
    }


    private AuctionsResponse readFromFile(JsonService jsonService, File file) {
        String fileBody = IOHelper.read(file.toPath());
        return jsonService.getJson(fileBody, AuctionsResponse.class);
    }

    public Map<Long, List<Auction>> getItems() {
        return items;
    }

    public List<Auction> getAuctions(ItemBean itemBean) {
        long itemId = itemBean.getItemId();
        List<Auction> auctions = items.get(itemId);
        return auctions == null ? new ArrayList<>() : auctions;
    }

    /**
     * Какие аукционы нужно купить, чтобы получить itemWithQuantity
     */
    public List<Auction> getBuyoutAuctions(ItemWithQuantity itemWithQuantity) {
        List<Auction> auctions = getAuctions(itemWithQuantity.getItem());
        auctions = onlyBuyout(auctions.stream()).collect(Collectors.toList());
        Collections.sort(auctions, new Comparator<Auction>() {
            @Override
            public int compare(Auction o1, Auction o2) {
                return o1.getBuyout().compareTo(o2.getBuyout());
            }
        });
        List<Auction> result = new ArrayList<>();
        int quantity = 0;
        for (Auction auction : auctions) {
            quantity += auction.getQuantity();
            result.add(auction);
            if (quantity >= itemWithQuantity.getQuantity()) {
                break;
            }
        }
        return result;
    }

    private Stream<Auction> onlyBuyout(Stream<Auction> stream) {
        return stream.filter(auction -> (auction.getBuyout() != null && auction.getBuyout() > 0));
    }


    public Optional<Auction> getMinBuyout(ItemBean item) {
        return getMinBuyout(getAuctions(item));
    }

    public Optional<Auction> getMinBuyout(List<Auction> auctions) {
        return auctions.stream().filter(auction -> (auction.getBuyout() != null && auction.getBuyout() > 0)).
                min(new Comparator<Auction>() {
                    @Override
                    public int compare(Auction o1, Auction o2) {
                        return o1.getBuyout().compareTo(o2.getBuyout());
                    }
                });
    }

    public Optional<Long> getMinBuyoutPriceForOne(List<Auction> auctions) {
        Optional<Auction> minBuyout = getMinBuyout(auctions);
        return minBuyout.isPresent() ? Optional.of(minBuyout.get().getBuyout() / minBuyout.get().getQuantity()) : Optional.empty();
    }

    public Optional<Long> getMinBuyoutPriceForOne(ItemBean item) {
        return getMinBuyoutPriceForOne(getAuctions(item));
    }


    public boolean inAuction(ItemBean item) {
        return items.containsKey(item.getItemId());
    }

    /**
     * Сколько стоит из аукционов купить вещь в нужном количестве
     */
    public long price(List<Auction> auctions, ItemWithQuantity itemWithQuantity) {
        long price = 0;
        int count = 0;
        for (Auction auction : auctions) {
            if (auction.getItem().equals(itemWithQuantity.getItem().getItemId())) {
                int quantity = itemWithQuantity.getQuantity();
                if (count + auction.getQuantity() >= quantity) {
                    int countToBuy = quantity - count;
                    price += auction.getBuyout() * countToBuy / auction.getQuantity();
                    break;
                }
                price += auction.getBuyout();
                count += auction.getQuantity();
            }
        }
        return price;
    }

}
