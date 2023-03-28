
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "display"
})
@Generated("jsonschema2pojo")
public class Armor {

    @JsonProperty("value")
    private Integer value;
    @JsonProperty("display")
    private Display display;

    @JsonProperty("value")
    public Integer getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Integer value) {
        this.value = value;
    }

    @JsonProperty("display")
    public Display getDisplay() {
        return display;
    }

    @JsonProperty("display")
    public void setDisplay(Display display) {
        this.display = display;
    }

}
