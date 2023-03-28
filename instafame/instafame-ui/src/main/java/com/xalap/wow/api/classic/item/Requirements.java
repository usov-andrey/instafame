
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "level"
})
@Generated("jsonschema2pojo")
public class Requirements {

    @JsonProperty("level")
    private Level level;

    @JsonProperty("level")
    public Level getLevel() {
        return level;
    }

    @JsonProperty("level")
    public void setLevel(Level level) {
        this.level = level;
    }

}
