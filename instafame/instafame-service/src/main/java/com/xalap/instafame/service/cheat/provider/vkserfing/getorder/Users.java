
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
        "limit_total",
        "current",
        "limit_system_per_hour",
        "users_limit_random",
        "automatic_current",
        "automatic_records"
})
public class Users {

    @JsonProperty("limit_total")
    private Integer limitTotal;
    @JsonProperty("current")
    private Integer current;
    @JsonProperty("limit_system_per_hour")
    private Integer limitSystemPerHour;
    @JsonProperty("users_limit_random")
    private String usersLimitRandom;
    @JsonProperty("automatic_current")
    private Integer automaticCurrent;
    @JsonProperty("automatic_records")
    private Integer automaticRecords;
    @JsonProperty("left")
    private Integer left;

    @JsonProperty("limit_total")
    public Integer getLimitTotal() {
        return limitTotal;
    }

    @JsonProperty("limit_total")
    public void setLimitTotal(Integer limitTotal) {
        this.limitTotal = limitTotal;
    }

    @JsonProperty("current")
    public Integer getCurrent() {
        return current;
    }

    @JsonProperty("current")
    public void setCurrent(Integer current) {
        this.current = current;
    }

    @JsonProperty("limit_system_per_hour")
    public Integer getLimitSystemPerHour() {
        return limitSystemPerHour;
    }

    @JsonProperty("limit_system_per_hour")
    public void setLimitSystemPerHour(Integer limitSystemPerHour) {
        this.limitSystemPerHour = limitSystemPerHour;
    }

    @JsonProperty("users_limit_random")
    public String getUsersLimitRandom() {
        return usersLimitRandom;
    }

    @JsonProperty("users_limit_random")
    public void setUsersLimitRandom(String usersLimitRandom) {
        this.usersLimitRandom = usersLimitRandom;
    }

    @JsonProperty("automatic_current")
    public Integer getAutomaticCurrent() {
        return automaticCurrent;
    }

    @JsonProperty("automatic_current")
    public void setAutomaticCurrent(Integer automaticCurrent) {
        this.automaticCurrent = automaticCurrent;
    }

    @JsonProperty("automatic_records")
    public Integer getAutomaticRecords() {
        return automaticRecords;
    }

    @JsonProperty("automatic_records")
    public void setAutomaticRecords(Integer automaticRecords) {
        this.automaticRecords = automaticRecords;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    @Override
    public String toString() {
        return "Users{" +
                "limitTotal=" + limitTotal +
                ", current=" + current +
                ", limitSystemPerHour=" + limitSystemPerHour +
                ", usersLimitRandom=" + usersLimitRandom +
                ", automaticCurrent=" + automaticCurrent +
                ", automaticRecords=" + automaticRecords +
                ", left=" + left +
                '}';
    }
}
