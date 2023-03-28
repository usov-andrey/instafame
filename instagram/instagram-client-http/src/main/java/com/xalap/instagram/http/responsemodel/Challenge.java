
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "challengeType",
        "errors",
        "fields",
        "navigation",
        "privacyPolicyUrl",
        "type"
})
public class Challenge {

    @JsonProperty("challengeType")
    private String challengeType;
    @JsonProperty("errors")
    private List<Object> errors = null;
    @JsonProperty("fields")
    private Fields fields;
    @JsonProperty("navigation")
    private Navigation navigation;
    @JsonProperty("privacyPolicyUrl")
    private String privacyPolicyUrl;
    @JsonProperty("type")
    private String type;

    @JsonProperty("challengeType")
    public String getChallengeType() {
        return challengeType;
    }

    @JsonProperty("challengeType")
    public void setChallengeType(String challengeType) {
        this.challengeType = challengeType;
    }

    @JsonProperty("errors")
    public List<Object> getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    @JsonProperty("fields")
    public Fields getFields() {
        return fields;
    }

    @JsonProperty("fields")
    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @JsonProperty("navigation")
    public Navigation getNavigation() {
        return navigation;
    }

    @JsonProperty("navigation")
    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    @JsonProperty("privacyPolicyUrl")
    public String getPrivacyPolicyUrl() {
        return privacyPolicyUrl;
    }

    @JsonProperty("privacyPolicyUrl")
    public void setPrivacyPolicyUrl(String privacyPolicyUrl) {
        this.privacyPolicyUrl = privacyPolicyUrl;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "challengeType='" + challengeType + '\'' +
                ", errors=" + errors +
                ", fields=" + fields +
                ", navigation=" + navigation +
                ", privacyPolicyUrl='" + privacyPolicyUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
