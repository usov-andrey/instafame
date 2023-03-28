
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
        "csrf_token",
        "viewer"
})
public class Config {

    @JsonProperty("csrf_token")
    private String csrfToken;
    @JsonProperty("viewer")
    private Viewer viewer;

    @JsonProperty("csrf_token")
    public String getCsrfToken() {
        return csrfToken;
    }

    @JsonProperty("csrf_token")
    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    @JsonProperty("viewer")
    public Viewer getViewer() {
        return viewer;
    }

    @JsonProperty("viewer")
    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
