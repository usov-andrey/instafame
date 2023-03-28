
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.datalayer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ecommerce"
})
public class DataLayer {

    @JsonProperty("ecommerce")
    private Ecommerce ecommerce;
    @JsonProperty("event")
    private String event;

    @JsonProperty("ecommerce")
    public Ecommerce getEcommerce() {
        return ecommerce;
    }

    @JsonProperty("ecommerce")
    public void setEcommerce(Ecommerce ecommerce) {
        this.ecommerce = ecommerce;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
