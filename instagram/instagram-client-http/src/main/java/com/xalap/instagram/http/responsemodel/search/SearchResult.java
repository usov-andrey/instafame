
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "users",
        "places",
        "hashtags",
        "has_more",
        "rank_token",
        "status"
})
public class SearchResult {

    @JsonProperty("users")
    private List<User> users = null;
    @JsonProperty("places")
    private List<Place> places = null;
    @JsonProperty("hashtags")
    private List<Hashtag> hashtags = null;
    @JsonProperty("has_more")
    private Boolean hasMore;
    @JsonProperty("rank_token")
    private String rankToken;
    @JsonProperty("status")
    private String status;

    @JsonProperty("users")
    public List<User> getUsers() {
        return users;
    }

    @JsonProperty("users")
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @JsonProperty("places")
    public List<Place> getPlaces() {
        return places;
    }

    @JsonProperty("places")
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @JsonProperty("hashtags")
    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    @JsonProperty("hashtags")
    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    @JsonProperty("has_more")
    public Boolean getHasMore() {
        return hasMore;
    }

    @JsonProperty("has_more")
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    @JsonProperty("rank_token")
    public String getRankToken() {
        return rankToken;
    }

    @JsonProperty("rank_token")
    public void setRankToken(String rankToken) {
        this.rankToken = rankToken;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
