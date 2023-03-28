
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
        "start_cursor",
        "end_cursor",
        "has_next_page",
        "has_previous_page"
})
public class PageInfo {

    @JsonProperty("start_cursor")
    private Object startCursor;
    @JsonProperty("end_cursor")
    private String endCursor;
    @JsonProperty("has_next_page")
    private Boolean hasNextPage;
    @JsonProperty("has_previous_page")
    private Boolean hasPreviousPage;

    @JsonProperty("start_cursor")
    public Object getStartCursor() {
        return startCursor;
    }

    @JsonProperty("start_cursor")
    public void setStartCursor(Object startCursor) {
        this.startCursor = startCursor;
    }

    @JsonProperty("end_cursor")
    public String getEndCursor() {
        return endCursor;
    }

    @JsonProperty("end_cursor")
    public void setEndCursor(String endCursor) {
        this.endCursor = endCursor;
    }

    @JsonProperty("has_next_page")
    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    @JsonProperty("has_next_page")
    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    @JsonProperty("has_previous_page")
    public Boolean getHasPreviousPage() {
        return hasPreviousPage;
    }

    @JsonProperty("has_previous_page")
    public void setHasPreviousPage(Boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
