package com.xalap.wow.item;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.wow.professionspell.SpellBean;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Entity(name = ItemBean.NAME)
public class ItemBean implements IdHolder<Integer> {

    public static final String NAME = "W$ItemClassic";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + ItemBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + ItemBean.NAME,
            sequenceName = "SEQ_" + ItemBean.NAME)
    private Integer id;
    private String name;
    @Column(unique = true)
    private long itemId;
    private Long sellPrice;//цена в медюках
    private Long buyPrice;//цена в медюках
    private BindType bindType;
    private ItemQuality quality;
    @ColumnDefault("false")
    private boolean canEquip;
    @ColumnDefault("false")
    private boolean canInAuction;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SpellBean creates;//Если вещь - это рецепт, то этот рецепт учит определенному скиллу, с помощью которого будет произведена какакя-то вещь

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BindType getBindType() {
        return bindType;
    }

    public void setBindType(BindType bindType) {
        this.bindType = bindType;
    }

    public SpellBean getCreates() {
        return creates;
    }

    public void setCreates(SpellBean creates) {
        this.creates = creates;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }

    public boolean isCanEquip() {
        return canEquip;
    }

    public void setCanEquip(boolean canEquip) {
        this.canEquip = canEquip;
    }

    public boolean isCanInAuction() {
        return canInAuction;
    }

    public void setCanInAuction(boolean canInAuction) {
        this.canInAuction = canInAuction;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemId=" + itemId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBean itemBean = (ItemBean) o;

        return id != null ? id.equals(itemBean.id) : itemBean.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
