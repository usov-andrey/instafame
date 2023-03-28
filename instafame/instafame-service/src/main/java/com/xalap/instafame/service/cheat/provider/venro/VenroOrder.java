/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.venro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "url",
        "start",
        "count",
        "remains",
        "status",
        "charge"
})
public class VenroOrder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("url")
    private String url;
    @JsonProperty("start")
    private String start;
    @JsonProperty("count")
    private String count;
    @JsonProperty("remains")
    private String remains;
    @JsonProperty("status")
    private String status;
    @JsonProperty("charge")
    private String charge;
    @JsonProperty("error")
    private String error;
    private String refund;
    private String cancel;
    private String check;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("start")
    public String getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(String start) {
        this.start = start;
    }

    @JsonProperty("count")
    public String getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(String count) {
        this.count = count;
    }

    @JsonProperty("remains")
    public String getRemains() {
        return remains;
    }

    @JsonProperty("remains")
    public void setRemains(String remains) {
        this.remains = remains;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("charge")
    public String getCharge() {
        return charge;
    }

    @JsonProperty("charge")
    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    @Override
    public String toString() {
        return "VenroOrder{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", start='" + start + '\'' +
                ", count='" + count + '\'' +
                ", remains='" + remains + '\'' +
                ", status='" + status + '\'' +
                ", charge='" + charge + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
