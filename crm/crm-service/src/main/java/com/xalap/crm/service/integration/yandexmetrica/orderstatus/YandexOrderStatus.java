
/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.orderstatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "humanized",
        "type"
})
@Generated("jsonschema2pojo")
public class YandexOrderStatus {

    @JsonProperty("id")
    private String id;
    @JsonProperty("humanized")
    private String humanized;
    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("humanized")
    public String getHumanized() {
        return humanized;
    }

    @JsonProperty("humanized")
    public void setHumanized(String humanized) {
        this.humanized = humanized;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

}
