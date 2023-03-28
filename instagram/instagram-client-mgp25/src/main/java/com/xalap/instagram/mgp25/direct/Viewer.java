
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.direct;

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
        "reel_auto_archive"
})
public class Viewer {

    @JsonProperty("pk")
    private Integer pk;
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
    @JsonProperty("reel_auto_archive")
    private String reelAutoArchive;

    @JsonProperty("pk")
    public Integer getPk() {
        return pk;
    }

    @JsonProperty("pk")
    public void setPk(Integer pk) {
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

    @JsonProperty("reel_auto_archive")
    public String getReelAutoArchive() {
        return reelAutoArchive;
    }

    @JsonProperty("reel_auto_archive")
    public void setReelAutoArchive(String reelAutoArchive) {
        this.reelAutoArchive = reelAutoArchive;
    }

}
