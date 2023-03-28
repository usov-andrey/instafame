package com.xalap.wow.auction;

/**
 * @author Усов Андрей
 * @since 20/12/2019
 */
public class PriceHelper {

    public static long inGold(Long price) {
        return price / 10000;
    }


    public static String toString(long price) {
        long gold = PriceHelper.inGold(price);
        long silver = (price - (gold * 10000)) / 100;
        long copper = (price - (gold * 10000) - (silver * 100));
        return gold + "g " + silver + "s " + copper + "c ";
    }
}
