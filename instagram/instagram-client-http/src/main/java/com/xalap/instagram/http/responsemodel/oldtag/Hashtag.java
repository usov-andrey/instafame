
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.oldtag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "edge_hashtag_to_media",
        "edge_hashtag_to_top_posts",
        "edge_hashtag_to_content_advisory"
})
public class Hashtag {

    @JsonProperty("name")
    private String name;
    @JsonProperty("edge_hashtag_to_media")
    private EdgeHashtagToMedia edgeHashtagToMedia;
    @JsonProperty("edge_hashtag_to_top_posts")
    private EdgeHashtagToTopPosts edgeHashtagToTopPosts;
    @JsonProperty("edge_hashtag_to_content_advisory")
    private EdgeHashtagToContentAdvisory edgeHashtagToContentAdvisory;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("edge_hashtag_to_media")
    public EdgeHashtagToMedia getEdgeHashtagToMedia() {
        return edgeHashtagToMedia;
    }

    @JsonProperty("edge_hashtag_to_media")
    public void setEdgeHashtagToMedia(EdgeHashtagToMedia edgeHashtagToMedia) {
        this.edgeHashtagToMedia = edgeHashtagToMedia;
    }

    @JsonProperty("edge_hashtag_to_top_posts")
    public EdgeHashtagToTopPosts getEdgeHashtagToTopPosts() {
        return edgeHashtagToTopPosts;
    }

    @JsonProperty("edge_hashtag_to_top_posts")
    public void setEdgeHashtagToTopPosts(EdgeHashtagToTopPosts edgeHashtagToTopPosts) {
        this.edgeHashtagToTopPosts = edgeHashtagToTopPosts;
    }

    @JsonProperty("edge_hashtag_to_content_advisory")
    public EdgeHashtagToContentAdvisory getEdgeHashtagToContentAdvisory() {
        return edgeHashtagToContentAdvisory;
    }

    @JsonProperty("edge_hashtag_to_content_advisory")
    public void setEdgeHashtagToContentAdvisory(EdgeHashtagToContentAdvisory edgeHashtagToContentAdvisory) {
        this.edgeHashtagToContentAdvisory = edgeHashtagToContentAdvisory;
    }

}
