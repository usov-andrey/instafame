package com.xalap.wow.tsm;

import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.HttpClientUtils;
import com.xalap.framework.utils.IOHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.wow.api.Region;
import com.xalap.wow.auction.ItemRegionSaleBean;
import com.xalap.wow.auction.ItemRegionSaleRepository;
import com.xalap.wow.item.ItemBean;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.LongConsumer;
import java.util.regex.Pattern;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
@Service
public class TSMService {

    private static final String URL = "http://api.tradeskillmaster.com/v1/item/EU/%s/%s?format=json&apiKey=%s";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String key = "fvzvoVjNVT1G8wb9AYdCgCk6tWM5C6TV";

    @Autowired
    private ItemRegionSaleRepository itemRegionSaleRepository;
    @Autowired
    private JsonService jsonService;

    public Map<Long, TsmItem> getTsmItemsData() {
        String filePath = "C:\\Program Files (x86)\\World of Warcraft\\_classic_\\Interface\\AddOns\\TradeSkillMaster_AppHelper\\AppData.lua";
        String data = IOHelper.read(Paths.get(filePath));
        Map<Long, TsmItem> result = new HashMap<>();
        readRegionData(data, result);
        readServerData(data, result);
        return result;
    }

    private void readServerData(String data, Map<Long, TsmItem> result) {
        String[] serverData = getMarketData(data, "Пламегор-Horde");
        for (String serverDatum : serverData) {
            String[] values = serverDatum.split(",");
            TsmItemData itemData = new TsmItemData();
            setValues(values,
                    itemData::setItemString,
                    itemData::setMarketValue,
                    itemData::setMinBuyout,
                    itemData::setHistorical,
                    itemData::setNumAuctions
            );
            long itemId = itemData.getItemString();
            TsmItem item = result.get(itemId);
            if (item == null) {
                item = new TsmItem();
                result.put(itemId, item);
            }
            item.setData(itemData);
        }
    }

    private void readRegionData(String data, Map<Long, TsmItem> result) {
        String[] regionData = getMarketData(data, "BCC-EU");
        for (String regionDatum : regionData) {
            String[] values = regionDatum.split(",");
            TsmItemRegionData itemRegionData = new TsmItemRegionData();
            setValues(values,
                    itemRegionData::setItemString,
                    itemRegionData::setRegionMarketValue,
                    itemRegionData::setRegionHistorical,
                    itemRegionData::setRegionSale,
                    itemRegionData::setRegionSoldPerDay,
                    itemRegionData::setRegionSalePercent
            );
            TsmItem item = new TsmItem();
            result.put(itemRegionData.getItemString(), item);
            item.setRegionData(itemRegionData);
        }
    }

    private void setValues(String[] values, LongConsumer... setters) {
        int index = 0;
        for (LongConsumer setter : setters) {
            setter.accept(Long.parseLong(values[index]));
            index++;
        }
    }

    private String[] getMarketData(String data, String market) {
        String value = StringHelper.getStringBetween(data, "AUCTIONDB_MARKET_DATA\",\"" + market + "\"",
                "--<AUCTIONDB_MARKET_DATA," + market);
        value = StringHelper.getStringBetween(value, "data={{", "}}}]])");
        return value.split(Pattern.quote("},{"));
    }

    public TsmItemResponse getInfo(Region realm, long itemId) {
        String url = String.format(URL, realm.name(), itemId, key);
        Request httpGet = Request.Get(url);
        String response = HttpClientUtils.execDefaultStringResponse(httpGet);
        log.debug("Item Response:" + response);
        return jsonService.getJson(response, TsmItemResponse.class);
    }

    public void update(ItemBean item, Region region) {
        ItemRegionSaleBean bean = itemRegionSaleRepository.findByItem(item);
        if (bean == null) {
            bean = new ItemRegionSaleBean();
            TsmItemResponse info = getInfo(region, item.getItemId());
            bean.setItem(item);
            bean.setHistoricalPrice(info.getHistoricalPrice());
            bean.setLastModified(new Date(info.getLastUpdated()));
            bean.setMarketValue(info.getMarketValue());
            bean.setMinBuyout(info.getMinBuyout());
            bean.setNumAuctions(info.getNumAuctions());
            bean.setQuantity(info.getQuantity());
            bean.setRegion(region);
            bean.setRegionAvgDailySold(info.getRegionAvgDailySold());
            bean.setRegionHistoricalPrice(info.getRegionHistoricalPrice());
            bean.setRegionMarketAvg(info.getRegionMarketAvg());
            bean.setRegionMinBuyoutAvg(info.getRegionMinBuyoutAvg());
            bean.setRegionQuantity(info.getRegionQuantity());
            bean.setRegionSaleAvg(info.getRegionSaleAvg());
            bean.setRegionSaleRate(info.getRegionSaleRate());
            itemRegionSaleRepository.save(bean);
        }
    }

    public void update(Collection<ItemBean> items, Region region) {
        for (ItemBean item : items) {
            update(item, region);
        }
    }

}
