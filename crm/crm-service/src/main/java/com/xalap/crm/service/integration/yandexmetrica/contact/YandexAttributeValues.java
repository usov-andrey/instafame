
/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "age",
        "first_seen",
        "dates_of_meetings"
})
@Generated("jsonschema2pojo")
public class YandexAttributeValues {

    @JsonProperty("age")
    private Integer age;
    @JsonProperty("first_seen")
    private String firstSeen;
    @JsonProperty("dates_of_meetings")
    private List<String> datesOfMeetings = null;

    @JsonProperty("age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("first_seen")
    public String getFirstSeen() {
        return firstSeen;
    }

    @JsonProperty("first_seen")
    public void setFirstSeen(String firstSeen) {
        this.firstSeen = firstSeen;
    }

    @JsonProperty("dates_of_meetings")
    public List<String> getDatesOfMeetings() {
        return datesOfMeetings;
    }

    @JsonProperty("dates_of_meetings")
    public void setDatesOfMeetings(List<String> datesOfMeetings) {
        this.datesOfMeetings = datesOfMeetings;
    }

}
