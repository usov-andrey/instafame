
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
        "data",
        "count"
})
public class Comments {

    @JsonProperty("data")
    private List<Datum_> data = null;
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("data")
    public List<Datum_> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Datum_> data) {
        this.data = data;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
