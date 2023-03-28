
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pk",
        "username",
        "full_name",
        "is_private",
        "profile_pic_url",
        "profile_pic_id",
        "is_verified",
        "has_anonymous_profile_picture",
        "follower_count",
        "byline",
        "mutual_followers_count",
        "following",
        "outgoing_request",
        "social_context",
        "search_social_context"
})
public class User_ {

    @JsonProperty("pk")
    private String pk;
    @JsonProperty("username")
    private String username;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
    @JsonProperty("profile_pic_id")
    private String profilePicId;
    @JsonProperty("is_verified")
    private Boolean isVerified;
    @JsonProperty("has_anonymous_profile_picture")
    private Boolean hasAnonymousProfilePicture;
    @JsonProperty("follower_count")
    private Integer followerCount;
    @JsonProperty("byline")
    private String byline;
    @JsonProperty("mutual_followers_count")
    private Double mutualFollowersCount;
    @JsonProperty("following")
    private Boolean following;
    @JsonProperty("outgoing_request")
    private Boolean outgoingRequest;
    @JsonProperty("social_context")
    private String socialContext;
    @JsonProperty("search_social_context")
    private String searchSocialContext;

    @JsonProperty("pk")
    public String getPk() {
        return pk;
    }

    @JsonProperty("pk")
    public void setPk(String pk) {
        this.pk = pk;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
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

    @JsonProperty("profile_pic_url")
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    @JsonProperty("profile_pic_url")
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    @JsonProperty("profile_pic_id")
    public String getProfilePicId() {
        return profilePicId;
    }

    @JsonProperty("profile_pic_id")
    public void setProfilePicId(String profilePicId) {
        this.profilePicId = profilePicId;
    }

    @JsonProperty("is_verified")
    public Boolean getIsVerified() {
        return isVerified;
    }

    @JsonProperty("is_verified")
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    @JsonProperty("has_anonymous_profile_picture")
    public Boolean getHasAnonymousProfilePicture() {
        return hasAnonymousProfilePicture;
    }

    @JsonProperty("has_anonymous_profile_picture")
    public void setHasAnonymousProfilePicture(Boolean hasAnonymousProfilePicture) {
        this.hasAnonymousProfilePicture = hasAnonymousProfilePicture;
    }

    @JsonProperty("follower_count")
    public Integer getFollowerCount() {
        return followerCount;
    }

    @JsonProperty("follower_count")
    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    @JsonProperty("byline")
    public String getByline() {
        return byline;
    }

    @JsonProperty("byline")
    public void setByline(String byline) {
        this.byline = byline;
    }

    @JsonProperty("mutual_followers_count")
    public Double getMutualFollowersCount() {
        return mutualFollowersCount;
    }

    @JsonProperty("mutual_followers_count")
    public void setMutualFollowersCount(Double mutualFollowersCount) {
        this.mutualFollowersCount = mutualFollowersCount;
    }

    @JsonProperty("following")
    public Boolean getFollowing() {
        return following;
    }

    @JsonProperty("following")
    public void setFollowing(Boolean following) {
        this.following = following;
    }

    @JsonProperty("outgoing_request")
    public Boolean getOutgoingRequest() {
        return outgoingRequest;
    }

    @JsonProperty("outgoing_request")
    public void setOutgoingRequest(Boolean outgoingRequest) {
        this.outgoingRequest = outgoingRequest;
    }

    @JsonProperty("social_context")
    public String getSocialContext() {
        return socialContext;
    }

    @JsonProperty("social_context")
    public void setSocialContext(String socialContext) {
        this.socialContext = socialContext;
    }

    @JsonProperty("search_social_context")
    public String getSearchSocialContext() {
        return searchSocialContext;
    }

    @JsonProperty("search_social_context")
    public void setSearchSocialContext(String searchSocialContext) {
        this.searchSocialContext = searchSocialContext;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
