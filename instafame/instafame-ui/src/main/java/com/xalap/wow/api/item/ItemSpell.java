
package com.xalap.wow.api.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "spellId",
        "nCharges",
        "consumable",
        "categoryId",
        "trigger",
        "scaledDescription"
})
public class ItemSpell {

    @JsonProperty("spellId")
    private Integer spellId;
    @JsonProperty("nCharges")
    private Integer nCharges;
    @JsonProperty("consumable")
    private Boolean consumable;
    @JsonProperty("categoryId")
    private Integer categoryId;
    @JsonProperty("trigger")
    private String trigger;
    @JsonProperty("scaledDescription")
    private String scaledDescription;

    @JsonProperty("spellId")
    public Integer getSpellId() {
        return spellId;
    }

    @JsonProperty("spellId")
    public void setSpellId(Integer spellId) {
        this.spellId = spellId;
    }

    @JsonProperty("nCharges")
    public Integer getNCharges() {
        return nCharges;
    }

    @JsonProperty("nCharges")
    public void setNCharges(Integer nCharges) {
        this.nCharges = nCharges;
    }

    @JsonProperty("consumable")
    public Boolean getConsumable() {
        return consumable;
    }

    @JsonProperty("consumable")
    public void setConsumable(Boolean consumable) {
        this.consumable = consumable;
    }

    @JsonProperty("categoryId")
    public Integer getCategoryId() {
        return categoryId;
    }

    @JsonProperty("categoryId")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @JsonProperty("trigger")
    public String getTrigger() {
        return trigger;
    }

    @JsonProperty("trigger")
    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    @JsonProperty("scaledDescription")
    public String getScaledDescription() {
        return scaledDescription;
    }

    @JsonProperty("scaledDescription")
    public void setScaledDescription(String scaledDescription) {
        this.scaledDescription = scaledDescription;
    }

}
