
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
        "name",
        "is_top_media_only",
        "profile_pic_url",
        "edge_hashtag_to_media",
        "edge_hashtag_to_top_posts",
        "edge_hashtag_to_content_advisory"
})
public class Hashtag {

    @JsonProperty("name")
    private String name;
    @JsonProperty("is_top_media_only")
    private Boolean isTopMediaOnly;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
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

    @JsonProperty("is_top_media_only")
    public Boolean getIsTopMediaOnly() {
        return isTopMediaOnly;
    }

    @JsonProperty("is_top_media_only")
    public void setIsTopMediaOnly(Boolean isTopMediaOnly) {
        this.isTopMediaOnly = isTopMediaOnly;
    }

    @JsonProperty("profile_pic_url")
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    @JsonProperty("profile_pic_url")
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
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
