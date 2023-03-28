
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "place",
        "position"
})
public class Place {

    @JsonProperty("place")
    private Place_ place;
    @JsonProperty("position")
    private Integer position;

    @JsonProperty("place")
    public Place_ getPlace() {
        return place;
    }

    @JsonProperty("place")
    public void setPlace(Place_ place) {
        this.place = place;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
