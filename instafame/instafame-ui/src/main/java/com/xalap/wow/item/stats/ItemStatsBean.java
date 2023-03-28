package com.xalap.wow.item.stats;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.wow.auction.file.AuctionFileBean;
import com.xalap.wow.item.ItemBean;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Entity()
@Table(name = ItemStatsBean.NAME,
        indexes = {
                @Index(columnList = "item_id,auctionFile_id", name = "Index_search")
        })
public class ItemStatsBean implements IdHolder<Integer> {

    public static final String NAME = "WOW$ItemStat";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + ItemStatsBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + ItemStatsBean.NAME,
            sequenceName = "SEQ_" + ItemStatsBean.NAME)
    private Integer id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemBean item;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AuctionFileBean auctionFile;

    @ColumnDefault("0")
    private int countAuc;//количество выставленных аукционнов
    private int newCount;//сколько было выставлено вещей за этот час
    /*
    private int newMinPriceCount;//сколько было выставлено вещей по более сниженной цене за этот час
    private int newSellersCount;//сколько новых продавцов появились на аукционе с этой вещью за часа
    private int newMinPriceSellersCount;//сколько новых продавцов появились на аукционе по более сниженной цене за этот час.
    */

    private int renewCount;//сколько было отменено и выставлено заново

    private int soldCount;//сколько было куплено вещей за этот час
    private long soldCost;//на какую сумму было продано вещей за этот час


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public AuctionFileBean getAuctionFile() {
        return auctionFile;
    }

    public void setAuctionFile(AuctionFileBean auctionFile) {
        this.auctionFile = auctionFile;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }

    public long getSoldCost() {
        return soldCost;
    }

    public void setSoldCost(long soldCost) {
        this.soldCost = soldCost;
    }

    public int getCountAuc() {
        return countAuc;
    }

    public void setCountAuc(int countAuc) {
        this.countAuc = countAuc;
    }

    @Override
    public String toString() {
        return "ItemStatsBean{" +
                "id=" + id +
                ", item=" + item +
                ", auctionFile=" + auctionFile +
                ", countAuc=" + countAuc +
                ", newCount=" + newCount +
                ", renewCount=" + renewCount +
                ", soldCount=" + soldCount +
                ", soldCost=" + soldCost +
                '}';
    }
}
