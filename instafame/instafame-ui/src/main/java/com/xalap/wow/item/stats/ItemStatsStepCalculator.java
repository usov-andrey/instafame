package com.xalap.wow.item.stats;

import com.xalap.wow.api.auction.Auction;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Вычисление статистики аукциона на одном шаге/для каждого продавца
 *
 * @author Усов Андрей
 * @since 06/12/2019
 */
public class ItemStatsStepCalculator extends ItemStatsStepData {

    public void calculate(Set<Auction> oldAuctions, Set<Auction> currentAuctions) {
        this.currentAuctions = currentAuctions;
        Set<Auction> auctions = new HashSet<>(oldAuctions);
        newAuctions = new HashSet<>();

        for (Auction auction : currentAuctions) {
            //Возможно это старый аукцион
            if (!auctions.contains(auction)) {
                newAuctions.add(auction);
            } else {
                //Исключаем существующий аукцион из старых
                auctions.remove(auction);
            }
        }
        //В старых остались только то, которых нет в текущем аукционе
        //Убираем expired
        expiredAuctions = getExpired(auctions);
        auctions.removeAll(expiredAuctions);
        //Убираем отмененные
        Map<Auction, Auction> reNew = getReNew(auctions, newAuctions);
        renewAuctions = reNew.keySet();
        auctions.removeAll(renewAuctions);
        newAuctions.removeAll(reNew.values());
        //Возможно кто-то отменил аукционы выставленные без buyout, удаляем такие аукционы
        Set<Auction> canceled = getWithoutBuyout(auctions);
        auctions.removeAll(canceled);
        soldAuctions = auctions;
    }

    private Set<Auction> getExpired(Set<Auction> auctions) {
        //Если в прошлый раз осталось меньше 2х часов до expired, то значит никто не купил
        return getAuctions(auctions, auction -> auction.hours() <= 2d);
    }

    /**
     * @return ключ - позиция аукциона старая, из auctions, значение - ему соответствующий аукцион из newAuctions
     */
    private Map<Auction, Auction> getReNew(Set<Auction> auctions, Set<Auction> newAuctions) {
        Map<Auction, Auction> result = new HashMap<>();
        for (Auction newAuction : newAuctions) {
            //Возможно новый - это отмененный и выставленный еще раз
            Optional<Auction> byItemAndQuantity = findByItemAndQuantity(auctions, newAuction);
            byItemAndQuantity.ifPresent(new Consumer<Auction>() {
                @Override
                public void accept(Auction auction) {
                    result.put(auction, newAuction);
                }
            });
        }
        return result;
    }

    private Set<Auction> getWithoutBuyout(Set<Auction> auctions) {
        return getAuctions(auctions, auction -> auction.getBuyout() == 0);

    }

    private Set<Auction> getAuctions(Set<Auction> auctions, Predicate<Auction> predicate) {
        return auctions.stream().filter(predicate).collect(Collectors.toSet());
    }


    /**
     * Ищем по Item + Quantity в коллекции
     */
    private Optional<Auction> findByItemAndQuantity(Collection<Auction> auctions, Auction value) {
        for (Auction auction : auctions) {
            if (auction.getItem().equals(value.getItem()) &&
                    auction.getQuantity().equals(value.getQuantity())) {
                return Optional.of(auction);
            }
        }
        return Optional.empty();
    }


}
