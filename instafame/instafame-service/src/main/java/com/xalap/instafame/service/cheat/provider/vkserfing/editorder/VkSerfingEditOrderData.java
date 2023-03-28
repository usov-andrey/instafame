
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.vkserfing.editorder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "edit"
})
public class VkSerfingEditOrderData {

    @JsonProperty("edit")
    private String edit;

    @JsonProperty("edit")
    public String getEdit() {
        return edit;
    }

    @JsonProperty("edit")
    public void setEdit(String edit) {
        this.edit = edit;
    }

}
