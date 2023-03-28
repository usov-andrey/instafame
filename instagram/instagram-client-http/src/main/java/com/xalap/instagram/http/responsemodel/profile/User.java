
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "biography",
        "blocked_by_viewer",
        "country_block",
        "external_url",
        "external_url_linkshimmed",
        "edge_followed_by",
        "followed_by_viewer",
        "edge_follow",
        "follows_viewer",
        "full_name",
        "has_blocked_viewer",
        "has_requested_viewer",
        "id",
        "is_private",
        "is_verified",
        "mutual_followers",
        "profile_pic_url",
        "profile_pic_url_hd",
        "requested_by_viewer",
        "username",
        "connected_fb_page",
        "edge_owner_to_timeline_media",
        "edge_saved_media",
        "edge_media_collections"
})
public class User {

    @JsonProperty("biography")
    private String biography;
    @JsonProperty("blocked_by_viewer")
    private Boolean blockedByViewer;
    @JsonProperty("country_block")
    private Boolean countryBlock;
    @JsonProperty("external_url")
    private String externalUrl;
    @JsonProperty("external_url_linkshimmed")
    private String externalUrlLinkshimmed;
    @JsonProperty("edge_followed_by")
    private EdgeFollowedBy edgeFollowedBy;
    @JsonProperty("followed_by_viewer")
    private Boolean followedByViewer;
    @JsonProperty("edge_follow")
    private EdgeFollow edgeFollow;
    @JsonProperty("follows_viewer")
    private Boolean followsViewer;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("has_blocked_viewer")
    private Boolean hasBlockedViewer;
    @JsonProperty("has_requested_viewer")
    private Boolean hasRequestedViewer;
    @JsonProperty("id")
    private String id;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    @JsonProperty("is_verified")
    private Boolean isVerified;
    @JsonProperty("mutual_followers")
    private Object mutualFollowers;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
    @JsonProperty("profile_pic_url_hd")
    private String profilePicUrlHd;
    @JsonProperty("requested_by_viewer")
    private Boolean requestedByViewer;
    @JsonProperty("username")
    private String username;
    @JsonProperty("connected_fb_page")
    private Object connectedFbPage;
    @JsonProperty("edge_owner_to_timeline_media")
    private EdgeOwnerToTimelineMedia edgeOwnerToTimelineMedia;
    @JsonProperty("edge_saved_media")
    private EdgeSavedMedia edgeSavedMedia;
    @JsonProperty("edge_media_collections")
    private EdgeMediaCollections edgeMediaCollections;

    @JsonProperty("biography")
    public String getBiography() {
        return biography;
    }

    @JsonProperty("biography")
    public void setBiography(String biography) {
        this.biography = biography;
    }

    @JsonProperty("blocked_by_viewer")
    public Boolean getBlockedByViewer() {
        return blockedByViewer;
    }

    @JsonProperty("blocked_by_viewer")
    public void setBlockedByViewer(Boolean blockedByViewer) {
        this.blockedByViewer = blockedByViewer;
    }

    @JsonProperty("country_block")
    public Boolean getCountryBlock() {
        return countryBlock;
    }

    @JsonProperty("country_block")
    public void setCountryBlock(Boolean countryBlock) {
        this.countryBlock = countryBlock;
    }

    @JsonProperty("external_url")
    public String getExternalUrl() {
        return externalUrl;
    }

    @JsonProperty("external_url")
    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    @JsonProperty("external_url_linkshimmed")
    public String getExternalUrlLinkshimmed() {
        return externalUrlLinkshimmed;
    }

    @JsonProperty("external_url_linkshimmed")
    public void setExternalUrlLinkshimmed(String externalUrlLinkshimmed) {
        this.externalUrlLinkshimmed = externalUrlLinkshimmed;
    }

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

    @JsonProperty("edge_follow")
    public EdgeFollow getEdgeFollow() {
        return edgeFollow;
    }

    @JsonProperty("edge_follow")
    public void setEdgeFollow(EdgeFollow edgeFollow) {
        this.edgeFollow = edgeFollow;
    }

    @JsonProperty("follows_viewer")
    public Boolean getFollowsViewer() {
        return followsViewer;
    }

    @JsonProperty("follows_viewer")
    public void setFollowsViewer(Boolean followsViewer) {
        this.followsViewer = followsViewer;
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

    @JsonProperty("has_requested_viewer")
    public Boolean getHasRequestedViewer() {
        return hasRequestedViewer;
    }

    @JsonProperty("has_requested_viewer")
    public void setHasRequestedViewer(Boolean hasRequestedViewer) {
        this.hasRequestedViewer = hasRequestedViewer;
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

    @JsonProperty("mutual_followers")
    public Object getMutualFollowers() {
        return mutualFollowers;
    }

    @JsonProperty("mutual_followers")
    public void setMutualFollowers(Object mutualFollowers) {
        this.mutualFollowers = mutualFollowers;
    }

    @JsonProperty("profile_pic_url")
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    @JsonProperty("profile_pic_url")
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    @JsonProperty("profile_pic_url_hd")
    public String getProfilePicUrlHd() {
        return profilePicUrlHd;
    }

    @JsonProperty("profile_pic_url_hd")
    public void setProfilePicUrlHd(String profilePicUrlHd) {
        this.profilePicUrlHd = profilePicUrlHd;
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

    @JsonProperty("connected_fb_page")
    public Object getConnectedFbPage() {
        return connectedFbPage;
    }

    @JsonProperty("connected_fb_page")
    public void setConnectedFbPage(Object connectedFbPage) {
        this.connectedFbPage = connectedFbPage;
    }

    @JsonProperty("edge_owner_to_timeline_media")
    public EdgeOwnerToTimelineMedia getEdgeOwnerToTimelineMedia() {
        return edgeOwnerToTimelineMedia;
    }

    @JsonProperty("edge_owner_to_timeline_media")
    public void setEdgeOwnerToTimelineMedia(EdgeOwnerToTimelineMedia edgeOwnerToTimelineMedia) {
        this.edgeOwnerToTimelineMedia = edgeOwnerToTimelineMedia;
    }

    @JsonProperty("edge_saved_media")
    public EdgeSavedMedia getEdgeSavedMedia() {
        return edgeSavedMedia;
    }

    @JsonProperty("edge_saved_media")
    public void setEdgeSavedMedia(EdgeSavedMedia edgeSavedMedia) {
        this.edgeSavedMedia = edgeSavedMedia;
    }

    @JsonProperty("edge_media_collections")
    public EdgeMediaCollections getEdgeMediaCollections() {
        return edgeMediaCollections;
    }

    @JsonProperty("edge_media_collections")
    public void setEdgeMediaCollections(EdgeMediaCollections edgeMediaCollections) {
        this.edgeMediaCollections = edgeMediaCollections;
    }

    @Override
    public String toString() {
        return "User{" +
                "biography='" + biography + '\'' +
                ", blockedByViewer=" + blockedByViewer +
                ", countryBlock=" + countryBlock +
                ", externalUrl=" + externalUrl +
                ", externalUrlLinkshimmed=" + externalUrlLinkshimmed +
                ", edgeFollowedBy=" + edgeFollowedBy +
                ", followedByViewer=" + followedByViewer +
                ", edgeFollow=" + edgeFollow +
                ", followsViewer=" + followsViewer +
                ", fullName='" + fullName + '\'' +
                ", hasBlockedViewer=" + hasBlockedViewer +
                ", hasRequestedViewer=" + hasRequestedViewer +
                ", id='" + id + '\'' +
                ", isPrivate=" + isPrivate +
                ", isVerified=" + isVerified +
                ", mutualFollowers=" + mutualFollowers +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", profilePicUrlHd='" + profilePicUrlHd + '\'' +
                ", requestedByViewer=" + requestedByViewer +
                ", username='" + username + '\'' +
                ", connectedFbPage=" + connectedFbPage +
                ", edgeOwnerToTimelineMedia=" + edgeOwnerToTimelineMedia +
                ", edgeSavedMedia=" + edgeSavedMedia +
                ", edgeMediaCollections=" + edgeMediaCollections +
                '}';
    }
}
