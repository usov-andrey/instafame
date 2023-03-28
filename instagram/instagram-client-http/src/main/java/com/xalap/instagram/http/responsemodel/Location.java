
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
        "id",
        "name",
        "has_public_page",
        "lat",
        "lng",
        "slug",
        "media",
        "top_posts"
})
public class Location {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("has_public_page")
    private Boolean hasPublicPage;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lng")
    private Double lng;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("media")
    private Media media;
    @JsonProperty("top_posts")
    private TopPosts topPosts;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("has_public_page")
    public Boolean getHasPublicPage() {
        return hasPublicPage;
    }

    @JsonProperty("has_public_page")
    public void setHasPublicPage(Boolean hasPublicPage) {
        this.hasPublicPage = hasPublicPage;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public Double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Double lng) {
        this.lng = lng;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
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
        return "Location{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hasPublicPage=" + hasPublicPage +
                ", lat=" + lat +
                ", lng=" + lng +
                ", slug='" + slug + '\'' +
                ", media=" + media +
                ", topPosts=" + topPosts +
                '}';
    }
}
