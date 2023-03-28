package com.xalap.wow.item.stats;

import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.DateHelper;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.auction.file.AuctionFileBean;

import java.util.*;
import java.util.function.Function;

/**
 * @author Усов Андрей
 * @since 06/12/2019
 */
public class ItemStatsStepData {

    protected Set<Auction> currentAuctions = new HashSet<>();
    protected Set<Auction> newAuctions = new HashSet<>();//новые аукционы, выставленные на этом шаге
    protected Set<Auction> expiredAuctions = new HashSet<>();//непроданные аукционы
    protected Set<Auction> renewAuctions = new HashSet<>();//отменены и выставлены заново
    protected Set<Auction> soldAuctions = new HashSet<>();//проданные аукционы

    public void addStep(ItemStatsStepData data) {
        currentAuctions.addAll(data.currentAuctions);
        newAuctions.addAll(data.newAuctions);
        expiredAuctions.addAll(data.expiredAuctions);
        renewAuctions.addAll(data.renewAuctions);
        soldAuctions.addAll(data.soldAuctions);
    }

    public void log(AuctionFileBean auctionFileBean) {
        System.out.println(DateHelper.getDateTime(auctionFileBean.getDataTime()));
        System.out.println("   Current:" + toList(currentAuctions) + " New:" + toList(newAuctions));
        if (!expiredAuctions.isEmpty()) {
            System.out.println("   Expired:" + toList(expiredAuctions));
        }
        if (!renewAuctions.isEmpty()) {
            System.out.println("   Canceled:" + toList(renewAuctions));
        }
        if (!soldAuctions.isEmpty()) {
            System.out.println("   Sold:" + toList(soldAuctions));
        }
    }

    private List<String> toList(Set<Auction> auctions) {
        List<Auction> list = new ArrayList<>(auctions);
        Collections.sort(list, new Comparator<Auction>() {
            @Override
            public int compare(Auction o1, Auction o2) {
                return o1.getBuyout().compareTo(o2.getBuyout());
            }
        });
        List<String> result = CollectionHelper.newArrayList(auctions, new Function<Auction, String>() {
            @Override
            public String apply(Auction auction) {
                return auction.getBuyout() + "(" + auction.getQuantity() + ")";
            }
        });
        return result;
    }

    public Set<Auction> getCurrentAuctions() {
        return currentAuctions;
    }

    public Set<Auction> getNewAuctions() {
        return newAuctions;
    }

    public Set<Auction> getExpiredAuctions() {
        return expiredAuctions;
    }

    public Set<Auction> getRenewAuctions() {
        return renewAuctions;
    }

    public Set<Auction> getSoldAuctions() {
        return soldAuctions;
    }

    public boolean isEmpty() {
        return currentAuctions.isEmpty() && newAuctions.isEmpty() && expiredAuctions.isEmpty() &&
                renewAuctions.isEmpty() && soldAuctions.isEmpty();
    }
}
