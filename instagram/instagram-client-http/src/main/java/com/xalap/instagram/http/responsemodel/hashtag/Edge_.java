
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.hashtag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "node"
})
public class Edge_ {

    @JsonProperty("node")
    private Node_ node;

    @JsonProperty("node")
    public Node_ getNode() {
        return node;
    }

    @JsonProperty("node")
    public void setNode(Node_ node) {
        this.node = node;
    }

}
