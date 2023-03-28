
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
        "avg_open_rate",
        "avg_click_rate"
})
public class Stats {

    @JsonProperty("avg_open_rate")
    private Integer avgOpenRate;
    @JsonProperty("avg_click_rate")
    private Integer avgClickRate;

    @JsonProperty("avg_open_rate")
    public Integer getAvgOpenRate() {
        return avgOpenRate;
    }

    @JsonProperty("avg_open_rate")
    public void setAvgOpenRate(Integer avgOpenRate) {
        this.avgOpenRate = avgOpenRate;
    }

    @JsonProperty("avg_click_rate")
    public Integer getAvgClickRate() {
        return avgClickRate;
    }

    @JsonProperty("avg_click_rate")
    public void setAvgClickRate(Integer avgClickRate) {
        this.avgClickRate = avgClickRate;
    }

}
