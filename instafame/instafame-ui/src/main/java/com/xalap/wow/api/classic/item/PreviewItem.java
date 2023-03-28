
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "item",
    "quality",
    "name",
    "media",
    "item_class",
    "item_subclass",
    "inventory_type",
    "binding",
    "armor",
    "stats",
    "sell_price",
    "requirements",
    "durability"
})
@Generated("jsonschema2pojo")
public class PreviewItem {

    @JsonProperty("item")
    private Item item;
    @JsonProperty("quality")
    private Quality__1 quality;
    @JsonProperty("name")
    private String name;
    @JsonProperty("media")
    private Media__1 media;
    @JsonProperty("item_class")
    private ItemClass__1 itemClass;
    @JsonProperty("item_subclass")
    private ItemSubclass__1 itemSubclass;
    @JsonProperty("inventory_type")
    private InventoryType__1 inventoryType;
    @JsonProperty("binding")
    private Binding binding;
    @JsonProperty("armor")
    private Armor armor;
    @JsonProperty("stats")
    private List<Stat> stats = null;
    @JsonProperty("sell_price")
    private SellPrice sellPrice;
    @JsonProperty("requirements")
    private Requirements requirements;
    @JsonProperty("durability")
    private Durability durability;

    @JsonProperty("item")
    public Item getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(Item item) {
        this.item = item;
    }

    @JsonProperty("quality")
    public Quality__1 getQuality() {
        return quality;
    }

    @JsonProperty("quality")
    public void setQuality(Quality__1 quality) {
        this.quality = quality;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("media")
    public Media__1 getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(Media__1 media) {
        this.media = media;
    }

    @JsonProperty("item_class")
    public ItemClass__1 getItemClass() {
        return itemClass;
    }

    @JsonProperty("item_class")
    public void setItemClass(ItemClass__1 itemClass) {
        this.itemClass = itemClass;
    }

    @JsonProperty("item_subclass")
    public ItemSubclass__1 getItemSubclass() {
        return itemSubclass;
    }

    @JsonProperty("item_subclass")
    public void setItemSubclass(ItemSubclass__1 itemSubclass) {
        this.itemSubclass = itemSubclass;
    }

    @JsonProperty("inventory_type")
    public InventoryType__1 getInventoryType() {
        return inventoryType;
    }

    @JsonProperty("inventory_type")
    public void setInventoryType(InventoryType__1 inventoryType) {
        this.inventoryType = inventoryType;
    }

    @JsonProperty("binding")
    public Binding getBinding() {
        return binding;
    }

    @JsonProperty("binding")
    public void setBinding(Binding binding) {
        this.binding = binding;
    }

    @JsonProperty("armor")
    public Armor getArmor() {
        return armor;
    }

    @JsonProperty("armor")
    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    @JsonProperty("stats")
    public List<Stat> getStats() {
        return stats;
    }

    @JsonProperty("stats")
    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    @JsonProperty("sell_price")
    public SellPrice getSellPrice() {
        return sellPrice;
    }

    @JsonProperty("sell_price")
    public void setSellPrice(SellPrice sellPrice) {
        this.sellPrice = sellPrice;
    }

    @JsonProperty("requirements")
    public Requirements getRequirements() {
        return requirements;
    }

    @JsonProperty("requirements")
    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

    @JsonProperty("durability")
    public Durability getDurability() {
        return durability;
    }

    @JsonProperty("durability")
    public void setDurability(Durability durability) {
        this.durability = durability;
    }

}
