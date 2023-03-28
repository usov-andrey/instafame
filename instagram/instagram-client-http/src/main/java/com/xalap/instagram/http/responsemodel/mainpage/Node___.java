
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
        "edge_followed_by",
        "followed_by_viewer",
        "full_name",
        "id",
        "is_private",
        "is_verified",
        "is_viewer",
        "profile_pic_url",
        "requested_by_viewer",
        "username"
})
public class Node___ {

    @JsonProperty("edge_followed_by")
    private EdgeFollowedBy edgeFollowedBy;
    @JsonProperty("followed_by_viewer")
    private Boolean followedByViewer;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("id")
    private String id;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    @JsonProperty("is_verified")
    private Boolean isVerified;
    @JsonProperty("is_viewer")
    private Boolean isViewer;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
    @JsonProperty("requested_by_viewer")
    private Boolean requestedByViewer;
    @JsonProperty("username")
    private String username;

    @JsonProperty("edge_followed_by")
    public EdgeFollowedBy getEdgeFollowedBy() {
        return edgeFollowedBy;
    }

    @JsonProperty("edge_followed_by")
    public void setEdgeFollowedBy(EdgeFollowedBy edgeFollowedBy) {
        this.edgeFollowedBy = edgeFollowedBy;
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

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("is_private")
    public Boolean getIsPrivate() {
        return isPrivate;
    }

    @JsonProperty("is_private")
    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @JsonProperty("is_verified")
    public Boolean getIsVerified() {
        return isVerified;
    }

    @JsonProperty("is_verified")
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    @JsonProperty("is_viewer")
    public Boolean getIsViewer() {
        return isViewer;
    }

    @JsonProperty("is_viewer")
    public void setIsViewer(Boolean isViewer) {
        this.isViewer = isViewer;
    }

    @JsonProperty("profile_pic_url")
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    @JsonProperty("profile_pic_url")
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    @JsonProperty("requested_by_viewer")
    public Boolean getRequestedByViewer() {
        return requestedByViewer;
    }

    @JsonProperty("requested_by_viewer")
    public void setRequestedByViewer(Boolean requestedByViewer) {
        this.requestedByViewer = requestedByViewer;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

}
