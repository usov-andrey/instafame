package com.xalap.wow.professionspell.reagent;

import com.xalap.framework.data.CrudService;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.auction.AuctionDataOld;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.professionspell.SpellBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 29/05/2019
 */
@Service
public class ReceiptReagentService extends CrudService<ReceiptReagentBean, ReceiptReagentRepository, Integer> {

    public ReceiptReagentBean getOrCreateReagent(SpellBean receiptBean, ItemBean itemBean, int quantity) {
        ReceiptReagentBean byReceiptAndItem = repository().findByReceiptAndItem(receiptBean, itemBean);
        if (byReceiptAndItem == null) {
            byReceiptAndItem = new ReceiptReagentBean();
            byReceiptAndItem.setReceipt(receiptBean);
            byReceiptAndItem.setItem(itemBean);
            byReceiptAndItem.setQuantity(quantity);
            byReceiptAndItem = save(byReceiptAndItem);
        }
        return byReceiptAndItem;
    }

    public long getPrice(AuctionDataOld auctionData, ReceiptReagentBean receiptReagentBean, List<Auction> reagentAuctionList) {
        long reagentPrice = auctionData.price(reagentAuctionList, receiptReagentBean);
        Long vendorBuyPrice = receiptReagentBean.getItem().getBuyPrice();
        if (vendorBuyPrice != null && reagentPrice == 0) {
            reagentPrice = vendorBuyPrice * receiptReagentBean.getQuantity();
        }
        return reagentPrice;
    }
}
