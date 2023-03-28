
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.mainpage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "node"
})
public class Edge__ {

    @JsonProperty("node")
    private Node__ node;

    @JsonProperty("node")
    public Node__ getNode() {
        return node;
    }

    @JsonProperty("node")
    public void setNode(Node__ node) {
        this.node = node;
    }

}
