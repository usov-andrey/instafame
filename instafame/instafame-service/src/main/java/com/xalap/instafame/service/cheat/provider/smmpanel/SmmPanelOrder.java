
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.smmpanel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "charge",
        "start_count",
        "status",
        "remains",
        "currency"
})
public class SmmPanelOrder {

    @JsonProperty("charge")
    private String charge;
    @JsonProperty("start_count")
    private String startCount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("remains")
    private String remains;
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("charge")
    public String getCharge() {
        return charge;
    }

    @JsonProperty("charge")
    public void setCharge(String charge) {
        this.charge = charge;
    }

    @JsonProperty("start_count")
    public String getStartCount() {
        return startCount;
    }

    @JsonProperty("start_count")
    public void setStartCount(String startCount) {
        this.startCount = startCount;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("remains")
    public String getRemains() {
        return remains;
    }

    @JsonProperty("remains")
    public void setRemains(String remains) {
        this.remains = remains;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
