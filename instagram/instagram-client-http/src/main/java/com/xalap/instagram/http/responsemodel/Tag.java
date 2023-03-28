
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "content_advisory",
        "media",
        "top_posts"
})
public class Tag {

    @JsonProperty("name")
    private String name;
    @JsonProperty("content_advisory")
    private Object contentAdvisory;
    @JsonProperty("media")
    private Media media;
    @JsonProperty("top_posts")
    private TopPosts topPosts;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("content_advisory")
    public Object getContentAdvisory() {
        return contentAdvisory;
    }

    @JsonProperty("content_advisory")
    public void setContentAdvisory(Object contentAdvisory) {
        this.contentAdvisory = contentAdvisory;
    }

    @JsonProperty("media")
    public Media getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(Media media) {
        this.media = media;
    }

    @JsonProperty("top_posts")
    public TopPosts getTopPosts() {
        return topPosts;
    }

    @JsonProperty("top_posts")
    public void setTopPosts(TopPosts topPosts) {
        this.topPosts = topPosts;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
