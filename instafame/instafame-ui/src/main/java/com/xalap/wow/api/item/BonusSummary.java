
package com.xalap.wow.api.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "defaultBonusLists",
        "chanceBonusLists",
        "bonusChances"
})
public class BonusSummary {

    @JsonProperty("defaultBonusLists")
    private List<Object> defaultBonusLists = null;
    @JsonProperty("chanceBonusLists")
    private List<Object> chanceBonusLists = null;
    @JsonProperty("bonusChances")
    private List<Object> bonusChances = null;

    @JsonProperty("defaultBonusLists")
    public List<Object> getDefaultBonusLists() {
        return defaultBonusLists;
    }

    @JsonProperty("defaultBonusLists")
    public void setDefaultBonusLists(List<Object> defaultBonusLists) {
        this.defaultBonusLists = defaultBonusLists;
    }

    @JsonProperty("chanceBonusLists")
    public List<Object> getChanceBonusLists() {
        return chanceBonusLists;
    }

    @JsonProperty("chanceBonusLists")
    public void setChanceBonusLists(List<Object> chanceBonusLists) {
        this.chanceBonusLists = chanceBonusLists;
    }

    @JsonProperty("bonusChances")
    public List<Object> getBonusChances() {
        return bonusChances;
    }

    @JsonProperty("bonusChances")
    public void setBonusChances(List<Object> bonusChances) {
        this.bonusChances = bonusChances;
    }

}
