
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
        "id",
        "profile_pic_url",
        "username",
        "edge_web_feed_timeline",
        "feed_reels_tray",
        "edge_suggested_user"
})
public class User {

    @JsonProperty("id")
    private String id;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
    @JsonProperty("username")
    private String username;
    @JsonProperty("edge_web_feed_timeline")
    private EdgeWebFeedTimeline edgeWebFeedTimeline;
    @JsonProperty("feed_reels_tray")
    private Object feedReelsTray;
    @JsonProperty("edge_suggested_user")
    private EdgeSuggestedUser edgeSuggestedUser;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("profile_pic_url")
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    @JsonProperty("profile_pic_url")
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("edge_web_feed_timeline")
    public EdgeWebFeedTimeline getEdgeWebFeedTimeline() {
        return edgeWebFeedTimeline;
    }

    @JsonProperty("edge_web_feed_timeline")
    public void setEdgeWebFeedTimeline(EdgeWebFeedTimeline edgeWebFeedTimeline) {
        this.edgeWebFeedTimeline = edgeWebFeedTimeline;
    }

    @JsonProperty("feed_reels_tray")
    public Object getFeedReelsTray() {
        return feedReelsTray;
    }

    @JsonProperty("feed_reels_tray")
    public void setFeedReelsTray(Object feedReelsTray) {
        this.feedReelsTray = feedReelsTray;
    }

    @JsonProperty("edge_suggested_user")
    public EdgeSuggestedUser getEdgeSuggestedUser() {
        return edgeSuggestedUser;
    }

    @JsonProperty("edge_suggested_user")
    public void setEdgeSuggestedUser(EdgeSuggestedUser edgeSuggestedUser) {
        this.edgeSuggestedUser = edgeSuggestedUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", username='" + username + '\'' +
                ", edgeWebFeedTimeline=" + edgeWebFeedTimeline +
                ", feedReelsTray=" + feedReelsTray +
                ", edgeSuggestedUser=" + edgeSuggestedUser +
                '}';
    }
}
