
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "id"
})
@Generated("jsonschema2pojo")
public class Item {

    @JsonProperty("key")
    private Key__3 key;
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("key")
    public Key__3 getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(Key__3 key) {
        this.key = key;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

}
