package com.xalap.wow.item.stats;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.auction.AuctionDataOld;
import com.xalap.wow.auction.AuctionService;
import com.xalap.wow.auction.file.AuctionFileBean;
import com.xalap.wow.auction.file.AuctionFileService;
import com.xalap.wow.item.ItemBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.ToLongFunction;

/**
 * Алгоритм вычисления:
 * берем текущие аукционы и преобразуем их в
 * для каждого пользователя Map аукционов, где для каждого аукциона задано время появления его на аукционе
 * Map<String, Map<Auction, AuctionFileBean>>
 * <p>
 * <p>
 * Рассмотрим случаи:
 * Пользователь выставил 3, один купили, он отменил один и выставил заново.
 * Результат: один старый аукцион и один новый. Одна продажа.
 * oldByUser: 1
 * newByUser: 1
 * changed: 2
 * <p>
 * берем каждый новый и ищем его в измененных, если находим с таким же размером стака, то считаем, что было перевыставление
 * убираем этот аукцион из changed
 * все оставшиеся аукционы в changed считаем продажами.
 *
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Service
public class ItemStatsService extends CrudService<ItemStatsBean, ItemStatsRepository, Integer> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuctionFileService auctionFileService;
    @Autowired
    private AuctionService auctionService;

    public void recount(Collection<ItemBean> itemBeanList) {
        Map<ItemBean, Map<String, Set<Auction>>> oldAuctions = new HashMap<>();
        for (ItemBean itemBean : itemBeanList) {
            oldAuctions.put(itemBean, new HashMap<>());
        }
        for (AuctionFileBean auctionFileBean : getAuctionFiles()) {
            AuctionDataOld auctionData = auctionService.getAuctionData(auctionFileBean);
            if (auctionData != null) {
                for (ItemBean itemBean : itemBeanList) {
                    Map<String, Set<Auction>> currentAuctions = getCurrentAuctions(itemBean, auctionData);
                    if (currentAuctions != null) {
                        recount(oldAuctions.get(itemBean), itemBean, auctionFileBean, currentAuctions);
                    }
                }
            }
        }
    }

    /**
     * Пересчитываем статистику по предмету
     */
    public void recount(ItemBean itemBean) {
        recount(Collections.singletonList(itemBean));
    }

    private void recount(Map<String, Set<Auction>> oldAuctions, ItemBean itemBean,
                         AuctionFileBean auctionFileBean, Map<String, Set<Auction>> currentAuctions) {
        ItemStatsStepData statsData = new ItemStatsStepData();

        Set<String> owners = new HashSet<>();
        owners.addAll(oldAuctions.keySet());
        owners.addAll(currentAuctions.keySet());

        for (String owner : owners) {
            //Расчитываем отдельно для каждого владельца товаров и потом обьединяем
            Set<Auction> auctions = getNotNull(currentAuctions.get(owner));
            ItemStatsStepCalculator calculator = new ItemStatsStepCalculator();
            calculator.calculate(getNotNull(oldAuctions.get(owner)), auctions);
            statsData.addStep(calculator);
            //Сохраняем текущие аукционы в качестве старых для следующего шага
            if (auctions.isEmpty()) {
                oldAuctions.remove(owner);
            } else {
                oldAuctions.put(owner, auctions);
            }
        }
        if (statsData.isEmpty()) {
            return;
        }
        //statsData.log(auctionFileBean);
        ItemStatsBean bean = createBean(itemBean, auctionFileBean);
        bean.setCountAuc(statsData.getCurrentAuctions().size());
        bean.setNewCount(statsData.getNewAuctions().size());
        bean.setRenewCount(statsData.getRenewAuctions().size());

        //Теперь в byOwner остались проданные
        for (Auction soldAuction : statsData.getSoldAuctions()) {
            bean.setSoldCount(bean.getSoldCount() + soldAuction.getQuantity());
            bean.setSoldCost(bean.getSoldCost() + soldAuction.getBuyout());
        }

        save(bean);
    }

    private Set<Auction> getNotNull(Set<Auction> auctions) {
        return auctions != null ? auctions : Collections.EMPTY_SET;
    }

    /**
     * @return список аукционов
     */
    private List<AuctionFileBean> getAuctionFiles() {
        //Нужно использовать только те, за которые есть файлы и между ними нет пропусков
        List<AuctionFileBean> result = new ArrayList<>();

        return result;
    }


    private Map<String, Set<Auction>> getCurrentAuctions(ItemBean itemBean, AuctionDataOld auctionData) {
        List<Auction> auctions = auctionData.getAuctions(itemBean);
        Map<String, Set<Auction>> currentAuctionsMap = new HashMap<>();
        for (Auction auction : auctions) {
            String owner = auction.getOwner();
            CollectionHelper.getHashSetOrCreate(currentAuctionsMap, owner).add(auction);
        }
        return currentAuctionsMap;
    }

    /**
     * @return Создаем или получаем уже существующий бин
     */
    private ItemStatsBean createBean(ItemBean itemBean, AuctionFileBean auctionFileBean) {
        ItemStatsBean oldBean = repository().findByItemAndAuctionFile(itemBean, auctionFileBean);
        ItemStatsBean bean = new ItemStatsBean();
        bean.setItem(itemBean);
        bean.setAuctionFile(auctionFileBean);
        if (oldBean != null) {
            bean.setId(oldBean.getId());
        }
        return bean;
    }

    public Map<ItemBean, List<ItemStatsBean>> getItemStatsMap(Set<ItemBean> itemBeanSet) {
        Map<ItemBean, List<ItemStatsBean>> result = new HashMap<>();
        List<ItemStatsBean> byItems = repository().findByItems(itemBeanSet);
        for (ItemStatsBean byItem : byItems) {
            CollectionHelper.getArrayListOrCreate(result, byItem.getItem()).add(byItem);
        }
        return result;
    }

    public double avgSoldByDay(List<ItemStatsBean> itemStatsBeanList) {
        OptionalDouble average = itemStatsBeanList.stream().mapToLong(ItemStatsBean::getSoldCount).average();
        if (average.isPresent()) {
            return average.getAsDouble() * 24;
        } else {
            return 0d;
        }
    }

    public double avgSellPrice(List<ItemStatsBean> itemStatsBeanList) {
        OptionalDouble average = itemStatsBeanList.stream().mapToLong(new ToLongFunction<ItemStatsBean>() {
            @Override
            public long applyAsLong(ItemStatsBean value) {
                return value.getSoldCount() != 0 ? value.getSoldCost() / value.getSoldCount() : 0;
            }
        }).filter(value -> value > 0).average();
        return average.isPresent() ? average.getAsDouble() : 0d;
    }
}
