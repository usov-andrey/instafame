
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
        "id",
        "profile_pic_url",
        "username",
        "blocked_by_viewer",
        "followed_by_viewer",
        "full_name",
        "has_blocked_viewer",
        "is_private",
        "is_unpublished",
        "is_verified",
        "requested_by_viewer"
})
public class Owner_ {

    @JsonProperty("id")
    private String id;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
    @JsonProperty("username")
    private String username;
    @JsonProperty("blocked_by_viewer")
    private Boolean blockedByViewer;
    @JsonProperty("followed_by_viewer")
    private Boolean followedByViewer;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("has_blocked_viewer")
    private Boolean hasBlockedViewer;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    @JsonProperty("is_unpublished")
    private Boolean isUnpublished;
    @JsonProperty("is_verified")
    private Boolean isVerified;
    @JsonProperty("requested_by_viewer")
    private Boolean requestedByViewer;

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

    @JsonProperty("blocked_by_viewer")
    public Boolean getBlockedByViewer() {
        return blockedByViewer;
    }

    @JsonProperty("blocked_by_viewer")
    public void setBlockedByViewer(Boolean blockedByViewer) {
        this.blockedByViewer = blockedByViewer;
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

    @JsonProperty("has_blocked_viewer")
    public Boolean getHasBlockedViewer() {
        return hasBlockedViewer;
    }

    @JsonProperty("has_blocked_viewer")
    public void setHasBlockedViewer(Boolean hasBlockedViewer) {
        this.hasBlockedViewer = hasBlockedViewer;
    }

    @JsonProperty("is_private")
    public Boolean getIsPrivate() {
        return isPrivate;
    }

    @JsonProperty("is_private")
    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @JsonProperty("is_unpublished")
    public Boolean getIsUnpublished() {
        return isUnpublished;
    }

    @JsonProperty("is_unpublished")
    public void setIsUnpublished(Boolean isUnpublished) {
        this.isUnpublished = isUnpublished;
    }

    @JsonProperty("is_verified")
    public Boolean getIsVerified() {
        return isVerified;
    }

    @JsonProperty("is_verified")
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    @JsonProperty("requested_by_viewer")
    public Boolean getRequestedByViewer() {
        return requestedByViewer;
    }

    @JsonProperty("requested_by_viewer")
    public void setRequestedByViewer(Boolean requestedByViewer) {
        this.requestedByViewer = requestedByViewer;
    }

}
