
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "display_strings"
})
@Generated("jsonschema2pojo")
public class SellPrice {

    @JsonProperty("value")
    private Integer value;
    @JsonProperty("display_strings")
    private DisplayStrings displayStrings;

    @JsonProperty("value")
    public Integer getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Integer value) {
        this.value = value;
    }

    @JsonProperty("display_strings")
    public DisplayStrings getDisplayStrings() {
        return displayStrings;
    }

    @JsonProperty("display_strings")
    public void setDisplayStrings(DisplayStrings displayStrings) {
        this.displayStrings = displayStrings;
    }

}
