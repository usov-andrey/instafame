
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
        "pk",
        "name",
        "address",
        "city",
        "short_name",
        "lng",
        "lat",
        "external_source",
        "facebook_places_id"
})
public class Location {

    @JsonProperty("pk")
    private String pk;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private String address;
    @JsonProperty("city")
    private String city;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("lng")
    private Double lng;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("external_source")
    private String externalSource;
    @JsonProperty("facebook_places_id")
    private Long facebookPlacesId;

    @JsonProperty("pk")
    public String getPk() {
        return pk;
    }

    @JsonProperty("pk")
    public void setPk(String pk) {
        this.pk = pk;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("short_name")
    public String getShortName() {
        return shortName;
    }

    @JsonProperty("short_name")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonProperty("lng")
    public Double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Double lng) {
        this.lng = lng;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("external_source")
    public String getExternalSource() {
        return externalSource;
    }

    @JsonProperty("external_source")
    public void setExternalSource(String externalSource) {
        this.externalSource = externalSource;
    }

    @JsonProperty("facebook_places_id")
    public Long getFacebookPlacesId() {
        return facebookPlacesId;
    }

    @JsonProperty("facebook_places_id")
    public void setFacebookPlacesId(Long facebookPlacesId) {
        this.facebookPlacesId = facebookPlacesId;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
