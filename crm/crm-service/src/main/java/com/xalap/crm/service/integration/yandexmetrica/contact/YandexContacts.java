
/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "contacts"
})
@Generated("jsonschema2pojo")
public class YandexContacts {

    @JsonProperty("contacts")
    private List<YandexContact> contacts = null;

    @JsonProperty("contacts")
    public List<YandexContact> getContacts() {
        return contacts;
    }

    @JsonProperty("contacts")
    public void setContacts(List<YandexContact> contacts) {
        this.contacts = contacts;
    }

}
