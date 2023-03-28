
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
        "is_new"
})
public class P____________ {

    @JsonProperty("is_new")
    private String isNew;

    @JsonProperty("is_new")
    public String getIsNew() {
        return isNew;
    }

    @JsonProperty("is_new")
    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

}
