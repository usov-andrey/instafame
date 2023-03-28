
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
        "logging_page_id",
        "show_suggested_profiles",
        "graphql"
})
public class ProfilePage {

    @JsonProperty("logging_page_id")
    private String loggingPageId;
    @JsonProperty("show_suggested_profiles")
    private Boolean showSuggestedProfiles;
    @JsonProperty("graphql")
    private Graphql graphql;

    @JsonProperty("logging_page_id")
    public String getLoggingPageId() {
        return loggingPageId;
    }

    @JsonProperty("logging_page_id")
    public void setLoggingPageId(String loggingPageId) {
        this.loggingPageId = loggingPageId;
    }

    @JsonProperty("show_suggested_profiles")
    public Boolean getShowSuggestedProfiles() {
        return showSuggestedProfiles;
    }

    @JsonProperty("show_suggested_profiles")
    public void setShowSuggestedProfiles(Boolean showSuggestedProfiles) {
        this.showSuggestedProfiles = showSuggestedProfiles;
    }

    @JsonProperty("graphql")
    public Graphql getGraphql() {
        return graphql;
    }

    @JsonProperty("graphql")
    public void setGraphql(Graphql graphql) {
        this.graphql = graphql;
    }

    @Override
    public String toString() {
        return "ProfilePage{" +
                "loggingPageId='" + loggingPageId + '\'' +
                ", showSuggestedProfiles=" + showSuggestedProfiles +
                ", graphql=" + graphql +
                '}';
    }
}
