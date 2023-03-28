
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.followby;

import com.fasterxml.jackson.annotation.*;
import com.xalap.instagram.http.responsemodel.FollowNode;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "node"
})
public class Edge {

    @JsonProperty("node")
    private FollowNode node;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("node")
    public FollowNode getNode() {
        return node;
    }

    @JsonProperty("node")
    public void setNode(FollowNode node) {
        this.node = node;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
