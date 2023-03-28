
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
        "following",
        "blocking",
        "is_private",
        "incoming_request",
        "outgoing_request",
        "is_bestie",
        "is_restricted"
})
public class FriendshipStatus {

    @JsonProperty("following")
    private Boolean following;
    @JsonProperty("blocking")
    private Boolean blocking;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    @JsonProperty("incoming_request")
    private Boolean incomingRequest;
    @JsonProperty("outgoing_request")
    private Boolean outgoingRequest;
    @JsonProperty("is_bestie")
    private Boolean isBestie;
    @JsonProperty("is_restricted")
    private Boolean isRestricted;

    @JsonProperty("following")
    public Boolean getFollowing() {
        return following;
    }

    @JsonProperty("following")
    public void setFollowing(Boolean following) {
        this.following = following;
    }

    @JsonProperty("blocking")
    public Boolean getBlocking() {
        return blocking;
    }

    @JsonProperty("blocking")
    public void setBlocking(Boolean blocking) {
        this.blocking = blocking;
    }

    @JsonProperty("is_private")
    public Boolean getIsPrivate() {
        return isPrivate;
    }

    @JsonProperty("is_private")
    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @JsonProperty("incoming_request")
    public Boolean getIncomingRequest() {
        return incomingRequest;
    }

    @JsonProperty("incoming_request")
    public void setIncomingRequest(Boolean incomingRequest) {
        this.incomingRequest = incomingRequest;
    }

    @JsonProperty("outgoing_request")
    public Boolean getOutgoingRequest() {
        return outgoingRequest;
    }

    @JsonProperty("outgoing_request")
    public void setOutgoingRequest(Boolean outgoingRequest) {
        this.outgoingRequest = outgoingRequest;
    }

    @JsonProperty("is_bestie")
    public Boolean getIsBestie() {
        return isBestie;
    }

    @JsonProperty("is_bestie")
    public void setIsBestie(Boolean isBestie) {
        this.isBestie = isBestie;
    }

    @JsonProperty("is_restricted")
    public Boolean getIsRestricted() {
        return isRestricted;
    }

    @JsonProperty("is_restricted")
    public void setIsRestricted(Boolean isRestricted) {
        this.isRestricted = isRestricted;
    }

}
