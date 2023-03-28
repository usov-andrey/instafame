
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
        "list_id",
        "categories",
        "total_items",
        "_links"
})
public class InterestCategories {

    @JsonProperty("list_id")
    private String listId;
    @JsonProperty("categories")
    private List<Category> categories = null;
    @JsonProperty("total_items")
    private Integer totalItems;
    @JsonProperty("_links")
    private List<Link> links = null;

    @JsonProperty("list_id")
    public String getListId() {
        return listId;
    }

    @JsonProperty("list_id")
    public void setListId(String listId) {
        this.listId = listId;
    }

    @JsonProperty("categories")
    public List<Category> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @JsonProperty("total_items")
    public Integer getTotalItems() {
        return totalItems;
    }

    @JsonProperty("total_items")
    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
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
