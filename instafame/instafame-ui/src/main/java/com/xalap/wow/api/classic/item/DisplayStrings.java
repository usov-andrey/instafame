
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "header",
    "gold",
    "silver",
    "copper"
})
@Generated("jsonschema2pojo")
public class DisplayStrings {

    @JsonProperty("header")
    private String header;
    @JsonProperty("gold")
    private String gold;
    @JsonProperty("silver")
    private String silver;
    @JsonProperty("copper")
    private String copper;

    @JsonProperty("header")
    public String getHeader() {
        return header;
    }

    @JsonProperty("header")
    public void setHeader(String header) {
        this.header = header;
    }

    @JsonProperty("gold")
    public String getGold() {
        return gold;
    }

    @JsonProperty("gold")
    public void setGold(String gold) {
        this.gold = gold;
    }

    @JsonProperty("silver")
    public String getSilver() {
        return silver;
    }

    @JsonProperty("silver")
    public void setSilver(String silver) {
        this.silver = silver;
    }

    @JsonProperty("copper")
    public String getCopper() {
        return copper;
    }

    @JsonProperty("copper")
    public void setCopper(String copper) {
        this.copper = copper;
    }

}
