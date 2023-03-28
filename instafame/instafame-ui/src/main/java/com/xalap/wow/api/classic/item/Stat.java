
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "value",
    "display"
})
@Generated("jsonschema2pojo")
public class Stat {

    @JsonProperty("type")
    private Type type;
    @JsonProperty("value")
    private Integer value;
    @JsonProperty("display")
    private Display__1 display;

    @JsonProperty("type")
    public Type getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Type type) {
        this.type = type;
    }

    @JsonProperty("value")
    public Integer getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Integer value) {
        this.value = value;
    }

    @JsonProperty("display")
    public Display__1 getDisplay() {
        return display;
    }

    @JsonProperty("display")
    public void setDisplay(Display__1 display) {
        this.display = display;
    }

}
