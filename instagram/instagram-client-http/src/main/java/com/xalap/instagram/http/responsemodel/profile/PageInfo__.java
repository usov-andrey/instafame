
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
        "has_next_page",
        "end_cursor"
})
public class PageInfo__ {

    @JsonProperty("has_next_page")
    private Boolean hasNextPage;
    @JsonProperty("end_cursor")
    private Object endCursor;

    @JsonProperty("has_next_page")
    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    @JsonProperty("has_next_page")
    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    @JsonProperty("end_cursor")
    public Object getEndCursor() {
        return endCursor;
    }

    @JsonProperty("end_cursor")
    public void setEndCursor(Object endCursor) {
        this.endCursor = endCursor;
    }

}
