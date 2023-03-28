
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
        "client_context",
        "item_id",
        "timestamp",
        "thread_id"
})
public class Payload {

    @JsonProperty("client_context")
    private String clientContext;
    @JsonProperty("item_id")
    private String itemId;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("thread_id")
    private String threadId;

    @JsonProperty("client_context")
    public String getClientContext() {
        return clientContext;
    }

    @JsonProperty("client_context")
    public void setClientContext(String clientContext) {
        this.clientContext = clientContext;
    }

    @JsonProperty("item_id")
    public String getItemId() {
        return itemId;
    }

    @JsonProperty("item_id")
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("thread_id")
    public String getThreadId() {
        return threadId;
    }

    @JsonProperty("thread_id")
    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

}
