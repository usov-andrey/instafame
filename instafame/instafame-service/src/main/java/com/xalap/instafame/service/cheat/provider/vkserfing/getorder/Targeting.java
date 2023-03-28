
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.vkserfing.getorder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sex",
        "relation",
        "age_from",
        "age_to",
        "friends_from",
        "friends_to",
        "photos_profile_from",
        "photos_profile_to",
        "wall_items_from",
        "wall_items_to",
        "month_reg_period",
        "automatic_records_limit",
        "country",
        "city"
})
public class Targeting {

    @JsonProperty("sex")
    private String sex;
    @JsonProperty("relation")
    private String relation;
    @JsonProperty("age_from")
    private String ageFrom;
    @JsonProperty("age_to")
    private String ageTo;
    @JsonProperty("friends_from")
    private String friendsFrom;
    @JsonProperty("friends_to")
    private String friendsTo;
    @JsonProperty("photos_profile_from")
    private String photosProfileFrom;
    @JsonProperty("photos_profile_to")
    private String photosProfileTo;
    @JsonProperty("wall_items_from")
    private String wallItemsFrom;
    @JsonProperty("wall_items_to")
    private String wallItemsTo;
    @JsonProperty("month_reg_period")
    private String monthRegPeriod;
    @JsonProperty("automatic_records_limit")
    private String automaticRecordsLimit;
    @JsonProperty("country")
    private Object country;
    @JsonProperty("city")
    private Object city;

    @JsonProperty("sex")
    public String getSex() {
        return sex;
    }

    @JsonProperty("sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonProperty("relation")
    public String getRelation() {
        return relation;
    }

    @JsonProperty("relation")
    public void setRelation(String relation) {
        this.relation = relation;
    }

    @JsonProperty("age_from")
    public String getAgeFrom() {
        return ageFrom;
    }

    @JsonProperty("age_from")
    public void setAgeFrom(String ageFrom) {
        this.ageFrom = ageFrom;
    }

    @JsonProperty("age_to")
    public String getAgeTo() {
        return ageTo;
    }

    @JsonProperty("age_to")
    public void setAgeTo(String ageTo) {
        this.ageTo = ageTo;
    }

    @JsonProperty("friends_from")
    public String getFriendsFrom() {
        return friendsFrom;
    }

    @JsonProperty("friends_from")
    public void setFriendsFrom(String friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    @JsonProperty("friends_to")
    public String getFriendsTo() {
        return friendsTo;
    }

    @JsonProperty("friends_to")
    public void setFriendsTo(String friendsTo) {
        this.friendsTo = friendsTo;
    }

    @JsonProperty("photos_profile_from")
    public String getPhotosProfileFrom() {
        return photosProfileFrom;
    }

    @JsonProperty("photos_profile_from")
    public void setPhotosProfileFrom(String photosProfileFrom) {
        this.photosProfileFrom = photosProfileFrom;
    }

    @JsonProperty("photos_profile_to")
    public String getPhotosProfileTo() {
        return photosProfileTo;
    }

    @JsonProperty("photos_profile_to")
    public void setPhotosProfileTo(String photosProfileTo) {
        this.photosProfileTo = photosProfileTo;
    }

    @JsonProperty("wall_items_from")
    public String getWallItemsFrom() {
        return wallItemsFrom;
    }

    @JsonProperty("wall_items_from")
    public void setWallItemsFrom(String wallItemsFrom) {
        this.wallItemsFrom = wallItemsFrom;
    }

    @JsonProperty("wall_items_to")
    public String getWallItemsTo() {
        return wallItemsTo;
    }

    @JsonProperty("wall_items_to")
    public void setWallItemsTo(String wallItemsTo) {
        this.wallItemsTo = wallItemsTo;
    }

    @JsonProperty("month_reg_period")
    public String getMonthRegPeriod() {
        return monthRegPeriod;
    }

    @JsonProperty("month_reg_period")
    public void setMonthRegPeriod(String monthRegPeriod) {
        this.monthRegPeriod = monthRegPeriod;
    }

    @JsonProperty("automatic_records_limit")
    public String getAutomaticRecordsLimit() {
        return automaticRecordsLimit;
    }

    @JsonProperty("automatic_records_limit")
    public void setAutomaticRecordsLimit(String automaticRecordsLimit) {
        this.automaticRecordsLimit = automaticRecordsLimit;
    }

    @JsonProperty("country")
    public Object getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(Object country) {
        this.country = country;
    }

    @JsonProperty("city")
    public Object getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(Object city) {
        this.city = city;
    }

}
