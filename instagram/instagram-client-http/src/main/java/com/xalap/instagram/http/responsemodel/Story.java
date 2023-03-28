
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.http.model.MediaActivity;
import com.xalap.instagram.http.responsemodel.profile.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "timestamp",
        "user",
        "media"
})
public class Story {

    @JsonProperty("type")
    private Integer type;
    @JsonProperty("timestamp")
    private Double timestamp;
    @JsonProperty("user")
    private User user;
    @JsonProperty("media")
    private ActivityFeedMedia media;

    @JsonProperty("type")
    public Integer getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonProperty("timestamp")
    public Double getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("media")
    public ActivityFeedMedia getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(ActivityFeedMedia media) {
        this.media = media;
    }

    public MediaActivity mediaActivity() {
        return new MediaActivity(getMedia().getId(), getUser().getUsername());
    }

    @Override
    public String toString() {
        return "Story{" +
                "type=" + type +
                ", timestamp=" + timestamp +
                ", user=" + user.getUsername() +
                ", media=" + media +
                '}';
    }
}
