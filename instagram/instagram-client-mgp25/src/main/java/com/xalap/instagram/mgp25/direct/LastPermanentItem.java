
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.direct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "item_id",
        "user_id",
        "timestamp",
        "item_type",
        "link",
        "client_context",
        "text"
})
public class LastPermanentItem {

    @JsonProperty("item_id")
    private String itemId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("item_type")
    private String itemType;
    @JsonProperty("link")
    private Link_ link;
    @JsonProperty("client_context")
    private String clientContext;
    @JsonProperty("text")
    private String text;

    @JsonProperty("item_id")
    public String getItemId() {
        return itemId;
    }

    @JsonProperty("item_id")
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("item_type")
    public String getItemType() {
        return itemType;
    }

    @JsonProperty("item_type")
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @JsonProperty("link")
    public Link_ getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(Link_ link) {
        this.link = link;
    }

    @JsonProperty("client_context")
    public String getClientContext() {
        return clientContext;
    }

    @JsonProperty("client_context")
    public void setClientContext(String clientContext) {
        this.clientContext = clientContext;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

}
