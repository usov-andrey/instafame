
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.follow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.http.responsemodel.FollowNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "node"
})
public class Edge {

    @JsonProperty("node")
    private FollowNode node;

    @JsonProperty("node")
    public FollowNode getNode() {
        return node;
    }

    @JsonProperty("node")
    public void setNode(FollowNode node) {
        this.node = node;
    }

}
