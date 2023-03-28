
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "display_string",
    "color"
})
@Generated("jsonschema2pojo")
public class Display__1 {

    @JsonProperty("display_string")
    private String displayString;
    @JsonProperty("color")
    private Color__1 color;

    @JsonProperty("display_string")
    public String getDisplayString() {
        return displayString;
    }

    @JsonProperty("display_string")
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    @JsonProperty("color")
    public Color__1 getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(Color__1 color) {
        this.color = color;
    }

}
