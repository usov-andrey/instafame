package com.xalap.wow.auction;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.wow.api.Region;
import com.xalap.wow.api.WowApi;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.api.classic.auction.ClassicAuction;
import com.xalap.wow.api.classic.auction.ClassicAuctionHouse;
import com.xalap.wow.auction.file.AuctionFileBean;
import com.xalap.wow.auction.file.AuctionFileService;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.item.ItemService;
import com.xalap.wow.professionspell.reagent.ReceiptReagentBean;
import com.xalap.wow.tsm.TSMService;
import com.xalap.wow.tsm.TsmItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 05/08/2019
 */
@Service
public class AuctionService extends CrudService<AuctionBean, AuctionRepository, Long> {

    @Autowired
    private AuctionFileService auctionFileService;
    @Autowired
    private JsonService jsonService;
    @Autowired
    private AuctionDataRepository repository;
    private AuctionDataOld auctionData;

    @Autowired
    private WowApi api;
    @Autowired
    private TSMService tsmService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private NotificationService notificationService;

    public void updateAuctions() {
        Region region = Region.flamegor;
        Map<Long, AuctionBean> auctionByIdMap = readAuction(region);
        updateRegionAuction(region, auctionByIdMap);
    }

    private void updateItems() {
        Map<Long, TsmItem> tsmItemsData = tsmService.getTsmItemsData();
        Map<Long, ItemBean> allItems = itemService.getAllItems();
        for (TsmItem value : tsmItemsData.values()) {
            if (value.getRegionData().soldPerDay() > 1) {
                long itemId = value.getItemId();
                if (!allItems.containsKey(itemId)) {
                    itemService.getItemClassic(itemId);
                }
            }
        }
    }

    private void countStatistics() {
        /**
         * для того, чтобы мне точнее оценивать вероятность, что я продам по такой-то цене такое-то количество товара
         * мне нужно изучить статистику аукционов по дням. Где за каждый день показывать график цены, за каждый час показывать объем товаров на рынке
         *
         */

    }

    private void findDeals(Map<Long, AuctionBean> auctionByIdMap) {
        Map<Long, TsmItem> tsmItemsData = tsmService.getTsmItemsData();
        processAuctions(tsmItemsData, auctionByIdMap);
    }

    private void processAuctions(Map<Long, TsmItem> tsmItemsData, Map<Long, AuctionBean> auctionByIdMap) {
        ProfitCalculator calculator = new ProfitCalculator(notificationService, itemService);
        Map<Long, List<AuctionBean>> auctionsByItemIdMap = convertToMapByItemId(auctionByIdMap);
        for (Map.Entry<Long, List<AuctionBean>> longListEntry : auctionsByItemIdMap.entrySet()) {
            long itemId = longListEntry.getKey();
            if (tsmItemsData.containsKey(itemId)) {
                //Вычисляем только если есть статистические данные вещи
                TsmItem tsmItem = tsmItemsData.get(itemId);
                if (tsmItem.getRegionData().soldPerDay() > 1) {
                    List<AuctionBean> auctionBeans = longListEntry.getValue();
                    auctionBeans.sort(Comparator.comparingLong(AuctionBean::buyoutForOne));
                    List<AuctionBean> buyoutAuctions = auctionBeans.stream()
                            .filter(AuctionBean::canBuyout)
                            .collect(Collectors.toList());
                    if (!buyoutAuctions.isEmpty()) {
                        calculator.processAuctions(buyoutAuctions, tsmItem);
                    }
                }
            }
        }
        calculator.writeBestPurchases();
    }

    private Map<Long, List<AuctionBean>> convertToMapByItemId(Map<Long, AuctionBean> auctionByIdMap) {
        Map<Long, List<AuctionBean>> result = new HashMap<>();
        for (AuctionBean value : auctionByIdMap.values()) {
            Long itemId = value.getItemId();
            CollectionHelper.getArrayListOrCreate(result, itemId).add(value);
        }
        return result;
    }

    /**
     * Обновляем информацию в базе по аукциону
     */
    private synchronized void updateRegionAuction(Region region, Map<Long, AuctionBean> auctionByIdMap) {
        findDeals(auctionByIdMap);
        Map<Long, AuctionBean> auctionFromDbByIdMap = getAuctionFromDb(region);
        boolean isNewData = false;
        for (Map.Entry<Long, AuctionBean> longAuctionBeanEntry : auctionFromDbByIdMap.entrySet()) {
            Long auctionId = longAuctionBeanEntry.getKey();
            AuctionBean auction = longAuctionBeanEntry.getValue();
            AuctionTimeLeft auctionTimeLeft = auction.getTimeLeft();
            if (auctionFromDbByIdMap.containsKey(auctionId)) {
                //товар все еще находится на аукционе
                AuctionBean auctionBean = auctionFromDbByIdMap.get(auctionId);
                if (auctionBean.getTimeLeft() != auctionTimeLeft) {
                    auction.setId(auctionBean.getId());
                    if (!isNewData) {
                        isNewData = true;
                        saveAuctionToDb(auctionByIdMap, region);
                    }
                }
            } else {
                //Появилась новая вещь
                if (!isNewData) {
                    isNewData = true;
                    saveAuctionToDb(auctionByIdMap, region);
                }
            }
        }
    }

    /**
     * Получаем данные о текущем аукционе от внешнего сервиса
     */
    private Map<Long, AuctionBean> readAuction(Region region) {
        ClassicAuctionHouse classicAuctionHouse = api.classicAuction(region);
        List<ClassicAuction> auctions = classicAuctionHouse.getAuctions();
        Map<Long, AuctionBean> result = new HashMap<>();
        for (ClassicAuction auction : auctions) {
            if (!result.containsKey(auction.getId())) {
                Long auctionId = auction.getId();
                AuctionTimeLeft auctionTimeLeft = AuctionTimeLeft.fromString(auction.getTimeLeft());
                AuctionBean bean = new AuctionBean();
                bean.setAuctionHouseId(region.getAuctionId());
                bean.setRealmId(region.getRealmId());
                bean.setBid(auction.getBid());
                bean.setBuyout(auction.getBuyout());
                bean.setItemId(auction.getItem().getId());
                bean.setTimeLeft(auctionTimeLeft);
                bean.setAuctionId(auctionId);
                bean.setQuantity(auction.getQuantity());
                result.put(auction.getId(), bean);
            }
        }
        return result;
    }

    /**
     * @return Данные о аукционе из базы данных
     */
    private Map<Long, AuctionBean> getAuctionFromDb(Region region) {
        Optional<AuctionDataBean> auctionDataBean = repository.findByRealmIdAndAuctionHouseId(region.getRealmId(), region.getAuctionId());
        if (auctionDataBean.isEmpty()) {
            return new HashMap<>();
        }
        return auctionDataBean.get().getData();
    }

    private void saveAuctionToDb(Map<Long, AuctionBean> auctionBeanMap, Region region) {
        AuctionDataBean bean = new AuctionDataBean();
        bean.setAuctionHouseId(region.getAuctionId());
        bean.setRealmId(region.getRealmId());
        AuctionData auctionData = new AuctionData();
        auctionData.putAll(auctionBeanMap);
        bean.setData(auctionData);
        try {
            bean.setId(1);
            repository.save(bean);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    private AuctionDataOld readAuction() {
        AuctionFileBean currentAuctionFile = auctionFileService.getCurrentAuctionFile();
        if (currentAuctionFile == null) {
            throw new IllegalStateException("No current auction");
        }
        auctionData = getAuctionData(currentAuctionFile);
        if (auctionData == null) {
            throw new IllegalStateException("Not found current auction file");
        }
        return auctionData;
    }

    public AuctionDataOld getAuctionData(AuctionFileBean currentAuctionFile) {
        File file = auctionFileService.filePath(currentAuctionFile).toFile();
        if (file.exists()) {
            return new AuctionDataOld().readFile(jsonService, file);
        } else {
            return null;
        }
    }

    public boolean isExistData(AuctionFileBean currentAuctionFile) {
        File file = auctionFileService.filePath(currentAuctionFile).toFile();
        return file.exists();
    }

    public synchronized AuctionDataOld getAuction() {
        if (auctionData == null) {
            auctionData = readAuction();
        }
        return auctionData;
    }

    public void clearCache() {
        auctionData = null;
    }

    public List<Auction> getReagentsAuctions(AuctionDataOld auctionData, List<ReceiptReagentBean> reagentBeanList) {
        List<Auction> result = new ArrayList<Auction>();
        for (ReceiptReagentBean receiptReagentBean : reagentBeanList) {
            //ПОтом нужно убрать эту заплатку
            if (receiptReagentBean.getItem() == null) {
                return new ArrayList<Auction>();
            }
            result.addAll(auctionData.getBuyoutAuctions(receiptReagentBean));
        }
        return result;
    }

}
