
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "name",
    "id"
})
@Generated("jsonschema2pojo")
public class ItemSubclass {

    @JsonProperty("key")
    private Key__2 key;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("key")
    public Key__2 getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(Key__2 key) {
        this.key = key;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
