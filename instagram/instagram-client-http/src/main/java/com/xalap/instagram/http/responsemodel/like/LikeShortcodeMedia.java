
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.like;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "shortcode",
        "edge_liked_by"
})
public class LikeShortcodeMedia {

    @JsonProperty("id")
    private String id;
    @JsonProperty("shortcode")
    private String shortcode;
    @JsonProperty("edge_liked_by")
    private EdgeLikedBy edgeLikedBy;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("shortcode")
    public String getShortcode() {
        return shortcode;
    }

    @JsonProperty("shortcode")
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @JsonProperty("edge_liked_by")
    public EdgeLikedBy getEdgeLikedBy() {
        return edgeLikedBy;
    }

    @JsonProperty("edge_liked_by")
    public void setEdgeLikedBy(EdgeLikedBy edgeLikedBy) {
        this.edgeLikedBy = edgeLikedBy;
    }

    @Override
    public String toString() {
        return "LikeShortcodeMedia{" +
                "id='" + id + '\'' +
                ", shortcode='" + shortcode + '\'' +
                ", edgeLikedBy=" + edgeLikedBy +
                '}';
    }
}
