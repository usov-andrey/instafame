
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.mailchimp.interest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.crm.service.integration.mailchimp.common.Link;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "category_id",
        "list_id",
        "id",
        "name",
        "subscriber_count",
        "display_order",
        "_links"
})
public class Interest {

    @JsonProperty("category_id")
    private String categoryId;
    @JsonProperty("list_id")
    private String listId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("subscriber_count")
    private String subscriberCount;
    @JsonProperty("display_order")
    private Integer displayOrder;
    @JsonProperty("_links")
    private List<Link> links = null;

    @JsonProperty("category_id")
    public String getCategoryId() {
        return categoryId;
    }

    @JsonProperty("category_id")
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @JsonProperty("list_id")
    public String getListId() {
        return listId;
    }

    @JsonProperty("list_id")
    public void setListId(String listId) {
        this.listId = listId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("subscriber_count")
    public String getSubscriberCount() {
        return subscriberCount;
    }

    @JsonProperty("subscriber_count")
    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    @JsonProperty("display_order")
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @JsonProperty("display_order")
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @JsonProperty("_links")
    public List<Link> getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
