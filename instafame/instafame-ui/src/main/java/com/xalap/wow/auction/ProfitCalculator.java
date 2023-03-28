package com.xalap.wow.auction;

import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.utils.StringHelper;
import com.xalap.wow.item.ItemService;
import com.xalap.wow.tsm.TsmItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Usov Andrey
 * @since 01.06.2022
 */
public class ProfitCalculator {

    /**
     * Сколько денег мы можем максимально вложить в покупку одного вида товаров
     */
    public static final int MAX_INVESTMENT_GOLD = 5000;
    /**
     * Сколько минимально мы хотим заработать, чтобы был смысл в этом участвовать
     */
    public static final int MIN_PROFIT_GOLD = 10;

    /**
     * Коэффициент по какой максимальной цене мы сможем продать по сравнению с marketValue.
     */
    public static final int MAX_SELL_MARKET_PRICE = 1;


    private final NotificationService notificationService;
    private final ItemService itemService;
    private final List<Purchase> purchases = new ArrayList<>();
    private static String lastMessage;

    public ProfitCalculator(NotificationService notificationService, ItemService itemService) {
        this.notificationService = notificationService;
        this.itemService = itemService;
    }

    public void processAuctions(List<AuctionBean> buyoutAuctions, TsmItem tsmItem) {
        int itemsToBuy = 0;//Количество купленных вещей
        long goldToPay = 0;//Количество золота, которое нужно потратить, чтобы купить все начальные аукционы
        Purchase bestPurchase = null;
        int index = 0;
        for (AuctionBean buyoutAuction : buyoutAuctions) {
            //Не первая позиция аукциона, возможно стоит купить, проверяем это
            if (goldToPay > 0) {
                //Если я все это продам по цене текущего аукциона, то сколько я заработаю
                //Максимальная цена продажи = MAX_SELL_MARKET_PRICE * MarketValue или цена следующего аукциона
                long maxSellPrice = Math.min(buyoutAuction.buyoutForOne(), tsmItem.getData().getMarketValue()
                        * MAX_SELL_MARKET_PRICE);
                long goldIncome = maxSellPrice * itemsToBuy;
                long taxes = goldIncome * 5 / 100;//Налог с продажи в 5 процентов платит продавец
                long profit = goldIncome - taxes - goldToPay;
                if (profit > 0 && PriceHelper.inGold(profit) >= MIN_PROFIT_GOLD) {
                    Purchase purchase = new Purchase(profit, goldToPay, itemsToBuy, index, buyoutAuctions, tsmItem);
                    if (bestPurchase == null || bestPurchase.getProfit() < profit) {
                        bestPurchase = purchase;
                    }
                }
            }

            int quantity = buyoutAuction.getQuantity();
            itemsToBuy += quantity;
            if (itemsToBuy > tsmItem.getRegionData().soldPerDay() ||
                    buyoutAuction.buyoutForOne() > tsmItem.getData().getMarketValue()) {
                break;
            }
            goldToPay += buyoutAuction.getBuyout();
            if (PriceHelper.inGold(goldToPay) > MAX_INVESTMENT_GOLD) {
                break;
            }
            index++;
        }
        if (bestPurchase != null) {
            purchases.add(bestPurchase);
        }
    }

    public void writeBestPurchases() {
        purchases.sort((o1, o2) -> Double.compare(o2.getProfit(), o1.getProfit()));
        //Выводим строку для быстрого поиска в TSM:
        List<String> tsmStrings = purchases.stream().map(purchase -> "i:" + purchase.getTsmItem().getItemId())
                .collect(Collectors.toList());
        StringBuilder message = new StringBuilder(StringHelper.join(tsmStrings, ";") + "\n");

        for (Purchase purchase : purchases) {
            TsmItem tsmItem = purchase.getTsmItem();
            long itemId = tsmItem.getItemId();
            message.append(itemService.getItem(itemId).getName()).append(" https://ru.tbc.wowhead.com/item=")
                    .append(itemId).append("\n");
            long lastBuyPrice = purchase.getAuctionBeanList().get(purchase.getIndex()).buyoutForOne();
            message.append("ROI:").append(purchase.roi())
                    .append(" Profit:").append(PriceHelper.toString(purchase.getProfit()))
                    .append(" Investment: ").append(PriceHelper.toString(purchase.getInvestment()))
                    .append(" Last auction price:").append(PriceHelper.toString(lastBuyPrice))
                    .append(" Auctions to buy:").append(purchase.getIndex())
                    .append(" Quantity to buy:").append(purchase.getQuantity())
                    .append(" in market:").append(purchase.getAuctionBeanList().size())
                    .append(" SoldByDay").append(tsmItem.getRegionData().soldPerDay())
                    .append(" PercentToSell: ").append(tsmItem.getRegionData().salePercent())
                    .append(" MarketValue:").append(PriceHelper.toString(tsmItem.getData().getMarketValue()))
                    .append("\n");

        }

        String stringMessage = message.toString();
        if (lastMessage == null || !lastMessage.equals(stringMessage)) {
            notificationService.sendMessage(stringMessage);
            lastMessage = stringMessage;
        }
    }
}
