package com.xalap.wow.auction;

import com.xalap.framework.domain.holder.IdHolder;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
@Entity()
@Table(name = AuctionBean.NAME)
public class AuctionBean implements IdHolder<Long> {

    public static final String NAME = "W$Auction";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + AuctionBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + AuctionBean.NAME,
            sequenceName = "SEQ_" + AuctionBean.NAME)
    private Long id;
    private Long auctionId;
    private Integer realmId;
    private Integer auctionHouseId;
    private Long itemId;
    private int quantity;
    private Long bid;
    private Long buyout;
    private AuctionTimeLeft timeLeft;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getRealmId() {
        return realmId;
    }

    public void setRealmId(Integer realmId) {
        this.realmId = realmId;
    }

    public Integer getAuctionHouseId() {
        return auctionHouseId;
    }

    public void setAuctionHouseId(Integer auctionHouseId) {
        this.auctionHouseId = auctionHouseId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    /**
     * @return Сколько золота нужно заплатить, чтобы купить все количество
     */
    public Long getBuyout() {
        return buyout;
    }

    public void setBuyout(Long buyout) {
        this.buyout = buyout;
    }

    public AuctionTimeLeft getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(AuctionTimeLeft timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String stringValue() {
        return "Buyout:" + PriceHelper.toString(buyoutForOne()) + " Quantity:" + quantity + " " + getTimeLeft();
    }

    /*
     * @return Сколько стоить одна единица товара
     */
    public long buyoutForOne() {
        return buyout / quantity;
    }

    @Override
    public String toString() {
        return "AuctionBean{" +
                "id=" + id +
                ", auctionId=" + auctionId +
                ", realmId=" + realmId +
                ", auctionHouseId=" + auctionHouseId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", bid=" + bid +
                ", buyout=" + buyout +
                ", timeLeft=" + timeLeft +
                '}';
    }

    public boolean canBuyout() {
        return getBuyout() > 0;
    }
}
