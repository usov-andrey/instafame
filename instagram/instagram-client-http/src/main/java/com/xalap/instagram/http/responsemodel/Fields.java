
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
        "choice",
        "country",
        "enrollment_time",
        "enrollment_date",
        "latitude",
        "longitude",
        "city",
        "platform",
        "user_agent"
})
public class Fields {

    @JsonProperty("choice")
    private String choice;
    @JsonProperty("country")
    private String country;
    @JsonProperty("enrollment_time")
    private Integer enrollmentTime;
    @JsonProperty("enrollment_date")
    private String enrollmentDate;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("city")
    private String city;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("choice")
    public String getChoice() {
        return choice;
    }

    @JsonProperty("choice")
    public void setChoice(String choice) {
        this.choice = choice;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("enrollment_time")
    public Integer getEnrollmentTime() {
        return enrollmentTime;
    }

    @JsonProperty("enrollment_time")
    public void setEnrollmentTime(Integer enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    @JsonProperty("enrollment_date")
    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    @JsonProperty("enrollment_date")
    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("platform")
    public String getPlatform() {
        return platform;
    }

    @JsonProperty("platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @JsonProperty("user_agent")
    public String getUserAgent() {
        return userAgent;
    }

    @JsonProperty("user_agent")
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
