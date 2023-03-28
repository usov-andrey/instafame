
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
        "biography",
        "external_url",
        "full_name",
        "has_profile_pic",
        "id",
        "profile_pic_url",
        "profile_pic_url_hd",
        "username"
})
public class Viewer {

    @JsonProperty("biography")
    private String biography;
    @JsonProperty("external_url")
    private Object externalUrl;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("has_profile_pic")
    private Boolean hasProfilePic;
    @JsonProperty("id")
    private String id;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
    @JsonProperty("profile_pic_url_hd")
    private String profilePicUrlHd;
    @JsonProperty("username")
    private String username;

    @JsonProperty("biography")
    public String getBiography() {
        return biography;
    }

    @JsonProperty("biography")
    public void setBiography(String biography) {
        this.biography = biography;
    }

    @JsonProperty("external_url")
    public Object getExternalUrl() {
        return externalUrl;
    }

    @JsonProperty("external_url")
    public void setExternalUrl(Object externalUrl) {
        this.externalUrl = externalUrl;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("has_profile_pic")
    public Boolean getHasProfilePic() {
        return hasProfilePic;
    }

    @JsonProperty("has_profile_pic")
    public void setHasProfilePic(Boolean hasProfilePic) {
        this.hasProfilePic = hasProfilePic;
    }

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

    @JsonProperty("profile_pic_url_hd")
    public String getProfilePicUrlHd() {
        return profilePicUrlHd;
    }

    @JsonProperty("profile_pic_url_hd")
    public void setProfilePicUrlHd(String profilePicUrlHd) {
        this.profilePicUrlHd = profilePicUrlHd;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
