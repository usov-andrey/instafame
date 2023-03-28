
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
        "nu_appsells_disabled"
})
public class P____________________ {

    @JsonProperty("nu_appsells_disabled")
    private String nuAppsellsDisabled;

    @JsonProperty("nu_appsells_disabled")
    public String getNuAppsellsDisabled() {
        return nuAppsellsDisabled;
    }

    @JsonProperty("nu_appsells_disabled")
    public void setNuAppsellsDisabled(String nuAppsellsDisabled) {
        this.nuAppsellsDisabled = nuAppsellsDisabled;
    }

}
