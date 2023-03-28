
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.medialist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "node"
})
public class Edge {

    @JsonProperty("node")
    private ShortcodeMediaInList node;

    @JsonProperty("node")
    public ShortcodeMediaInList getNode() {
        return node;
    }

    @JsonProperty("node")
    public void setNode(ShortcodeMediaInList node) {
        this.node = node;
    }

}
