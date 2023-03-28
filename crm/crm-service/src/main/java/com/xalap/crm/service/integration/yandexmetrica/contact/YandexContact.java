
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
        "uniq_id",
        "name",
        "create_date_time",
        "update_date_time",
        "client_ids",
        "emails",
        "phones",
        "attribute_values"
})
@Generated("jsonschema2pojo")
public class YandexContact {

    @JsonProperty("uniq_id")
    private String uniqId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("create_date_time")
    private String createDateTime;
    @JsonProperty("update_date_time")
    private String updateDateTime;
    @JsonProperty("client_ids")
    private List<String> clientIds = null;
    @JsonProperty("emails")
    private List<String> emails = null;
    @JsonProperty("phones")
    private List<String> phones = null;
    @JsonProperty("attribute_values")
    private YandexAttributeValues attributeValues;

    @JsonProperty("uniq_id")
    public String getUniqId() {
        return uniqId;
    }

    @JsonProperty("uniq_id")
    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("create_date_time")
    public String getCreateDateTime() {
        return createDateTime;
    }

    @JsonProperty("create_date_time")
    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    @JsonProperty("update_date_time")
    public String getUpdateDateTime() {
        return updateDateTime;
    }

    @JsonProperty("update_date_time")
    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @JsonProperty("client_ids")
    public List<String> getClientIds() {
        return clientIds;
    }

    @JsonProperty("client_ids")
    public void setClientIds(List<String> clientIds) {
        this.clientIds = clientIds;
    }

    @JsonProperty("emails")
    public List<String> getEmails() {
        return emails;
    }

    @JsonProperty("emails")
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @JsonProperty("phones")
    public List<String> getPhones() {
        return phones;
    }

    @JsonProperty("phones")
    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    @JsonProperty("attribute_values")
    public YandexAttributeValues getAttributeValues() {
        return attributeValues;
    }

    @JsonProperty("attribute_values")
    public void setAttributeValues(YandexAttributeValues attributeValues) {
        this.attributeValues = attributeValues;
    }

}
