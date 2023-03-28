/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.media;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "node"
})
public class SideCarEdge {

    @JsonProperty("node")
    private ShortcodeMedia node;

    @JsonProperty("node")
    public ShortcodeMedia getNode() {
        return node;
    }

    @JsonProperty("node")
    public void setNode(ShortcodeMedia node) {
        this.node = node;
    }

}

