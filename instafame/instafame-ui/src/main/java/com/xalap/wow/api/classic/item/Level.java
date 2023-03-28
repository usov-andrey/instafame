
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "display_string"
})
@Generated("jsonschema2pojo")
public class Level {

    @JsonProperty("value")
    private Integer value;
    @JsonProperty("display_string")
    private String displayString;

    @JsonProperty("value")
    public Integer getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Integer value) {
        this.value = value;
    }

    @JsonProperty("display_string")
    public String getDisplayString() {
        return displayString;
    }

    @JsonProperty("display_string")
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

}
