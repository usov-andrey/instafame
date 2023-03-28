
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.mainpage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.http.responsemodel.Challenge;
import com.xalap.instagram.http.responsemodel.profile.ProfilePage;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "FeedPage",
        "Challenge"
})
public class EntryData {

    @JsonProperty("ProfilePage")
    private List<ProfilePage> profilePage = null;
    @JsonProperty("FeedPage")
    private List<FeedPage> feedPage = null;
    @JsonProperty("Challenge")
    private List<Challenge> challenge = null;

    @JsonProperty("ProfilePage")
    public List<ProfilePage> getProfilePage() {
        return profilePage;
    }

    @JsonProperty("ProfilePage")
    public void setProfilePage(List<ProfilePage> profilePage) {
        this.profilePage = profilePage;
    }

    @JsonProperty("FeedPage")
    public List<FeedPage> getFeedPage() {
        return feedPage;
    }

    @JsonProperty("FeedPage")
    public void setFeedPage(List<FeedPage> feedPage) {
        this.feedPage = feedPage;
    }

    @JsonProperty("Challenge")
    public List<Challenge> getChallenge() {
        return challenge;
    }

    @JsonProperty("Challenge")
    public void setChallenge(List<Challenge> challenge) {
        this.challenge = challenge;
    }

    @Override
    public String toString() {
        return "EntryData{" +
                "profilePage=" + profilePage +
                ", feedPage=" + feedPage +
                ", challenge=" + challenge +
                '}';
    }
}
