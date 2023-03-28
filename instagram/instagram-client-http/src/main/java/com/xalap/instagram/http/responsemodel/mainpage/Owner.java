
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
        "followed_by_viewer",
        "full_name",
        "is_private",
        "requested_by_viewer",
        "blocked_by_viewer",
        "has_blocked_viewer"
})
public class Owner {

    @JsonProperty("id")
    private String id;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
    @JsonProperty("username")
    private String username;
    @JsonProperty("followed_by_viewer")
    private Boolean followedByViewer;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    @JsonProperty("requested_by_viewer")
    private Boolean requestedByViewer;
    @JsonProperty("blocked_by_viewer")
    private Boolean blockedByViewer;
    @JsonProperty("has_blocked_viewer")
    private Boolean hasBlockedViewer;

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

    @JsonProperty("followed_by_viewer")
    public Boolean getFollowedByViewer() {
        return followedByViewer;
    }

    @JsonProperty("followed_by_viewer")
    public void setFollowedByViewer(Boolean followedByViewer) {
        this.followedByViewer = followedByViewer;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("is_private")
    public Boolean getIsPrivate() {
        return isPrivate;
    }

    @JsonProperty("is_private")
    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @JsonProperty("requested_by_viewer")
    public Boolean getRequestedByViewer() {
        return requestedByViewer;
    }

    @JsonProperty("requested_by_viewer")
    public void setRequestedByViewer(Boolean requestedByViewer) {
        this.requestedByViewer = requestedByViewer;
    }

    @JsonProperty("blocked_by_viewer")
    public Boolean getBlockedByViewer() {
        return blockedByViewer;
    }

    @JsonProperty("blocked_by_viewer")
    public void setBlockedByViewer(Boolean blockedByViewer) {
        this.blockedByViewer = blockedByViewer;
    }

    @JsonProperty("has_blocked_viewer")
    public Boolean getHasBlockedViewer() {
        return hasBlockedViewer;
    }

    @JsonProperty("has_blocked_viewer")
    public void setHasBlockedViewer(Boolean hasBlockedViewer) {
        this.hasBlockedViewer = hasBlockedViewer;
    }

}
