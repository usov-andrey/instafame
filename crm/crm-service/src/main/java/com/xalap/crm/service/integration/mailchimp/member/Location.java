
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.mailchimp.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "latitude",
        "longitude",
        "gmtoff",
        "dstoff",
        "country_code",
        "timezone"
})
public class Location {

    @JsonProperty("latitude")
    private Integer latitude;
    @JsonProperty("longitude")
    private Integer longitude;
    @JsonProperty("gmtoff")
    private Integer gmtoff;
    @JsonProperty("dstoff")
    private Integer dstoff;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("latitude")
    public Integer getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Integer getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("gmtoff")
    public Integer getGmtoff() {
        return gmtoff;
    }

    @JsonProperty("gmtoff")
    public void setGmtoff(Integer gmtoff) {
        this.gmtoff = gmtoff;
    }

    @JsonProperty("dstoff")
    public Integer getDstoff() {
        return dstoff;
    }

    @JsonProperty("dstoff")
    public void setDstoff(Integer dstoff) {
        this.dstoff = dstoff;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
