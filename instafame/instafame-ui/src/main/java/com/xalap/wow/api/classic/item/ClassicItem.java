
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_links",
    "id",
    "name",
    "quality",
    "level",
    "required_level",
    "media",
    "item_class",
    "item_subclass",
    "inventory_type",
    "purchase_price",
    "sell_price",
    "max_count",
    "is_equippable",
    "is_stackable",
    "preview_item"
})
@Generated("jsonschema2pojo")
public class ClassicItem {

    @JsonProperty("_links")
    private Links links;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quality")
    private Quality quality;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("required_level")
    private Integer requiredLevel;
    @JsonProperty("media")
    private Media media;
    @JsonProperty("item_class")
    private ItemClass itemClass;
    @JsonProperty("item_subclass")
    private ItemSubclass itemSubclass;
    @JsonProperty("inventory_type")
    private InventoryType inventoryType;
    @JsonProperty("purchase_price")
    private Long purchasePrice;
    @JsonProperty("sell_price")
    private Long sellPrice;
    @JsonProperty("max_count")
    private Integer maxCount;
    @JsonProperty("is_equippable")
    private Boolean isEquippable;
    @JsonProperty("is_stackable")
    private Boolean isStackable;
    @JsonProperty("preview_item")
    private PreviewItem previewItem;

    @JsonProperty("_links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("quality")
    public Quality getQuality() {
        return quality;
    }

    @JsonProperty("quality")
    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    @JsonProperty("level")
    public void setLevel(Integer level) {
        this.level = level;
    }

    @JsonProperty("required_level")
    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    @JsonProperty("required_level")
    public void setRequiredLevel(Integer requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @JsonProperty("media")
    public Media getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(Media media) {
        this.media = media;
    }

    @JsonProperty("item_class")
    public ItemClass getItemClass() {
        return itemClass;
    }

    @JsonProperty("item_class")
    public void setItemClass(ItemClass itemClass) {
        this.itemClass = itemClass;
    }

    @JsonProperty("item_subclass")
    public ItemSubclass getItemSubclass() {
        return itemSubclass;
    }

    @JsonProperty("item_subclass")
    public void setItemSubclass(ItemSubclass itemSubclass) {
        this.itemSubclass = itemSubclass;
    }

    @JsonProperty("inventory_type")
    public InventoryType getInventoryType() {
        return inventoryType;
    }

    @JsonProperty("inventory_type")
    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    @JsonProperty("purchase_price")
    public Long getPurchasePrice() {
        return purchasePrice;
    }

    @JsonProperty("purchase_price")
    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @JsonProperty("sell_price")
    public Long getSellPrice() {
        return sellPrice;
    }

    @JsonProperty("sell_price")
    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    @JsonProperty("max_count")
    public Integer getMaxCount() {
        return maxCount;
    }

    @JsonProperty("max_count")
    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    @JsonProperty("is_equippable")
    public Boolean getIsEquippable() {
        return isEquippable;
    }

    @JsonProperty("is_equippable")
    public void setIsEquippable(Boolean isEquippable) {
        this.isEquippable = isEquippable;
    }

    @JsonProperty("is_stackable")
    public Boolean getIsStackable() {
        return isStackable;
    }

    @JsonProperty("is_stackable")
    public void setIsStackable(Boolean isStackable) {
        this.isStackable = isStackable;
    }

    @JsonProperty("preview_item")
    public PreviewItem getPreviewItem() {
        return previewItem;
    }

    @JsonProperty("preview_item")
    public void setPreviewItem(PreviewItem previewItem) {
        this.previewItem = previewItem;
    }


    @Override
    public String toString() {
        return "ClassicItem{" +
                "links=" + links +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", quality=" + quality +
                ", level=" + level +
                ", requiredLevel=" + requiredLevel +
                ", media=" + media +
                ", itemClass=" + itemClass +
                ", itemSubclass=" + itemSubclass +
                ", inventoryType=" + inventoryType +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                ", maxCount=" + maxCount +
                ", isEquippable=" + isEquippable +
                ", isStackable=" + isStackable +
                ", previewItem=" + previewItem +
                '}';
    }
}
