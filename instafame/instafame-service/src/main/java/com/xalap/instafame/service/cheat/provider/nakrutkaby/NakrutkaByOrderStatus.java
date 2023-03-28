/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.nakrutkaby;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "charge",
        "status",
        "link",
        "quantity",
        "start_count",
        "remains"
})
public class NakrutkaByOrderStatus extends NakrutkaByResponse {

    @JsonProperty("charge")
    private String charge;
    @JsonProperty("status")
    private String status;
    @JsonProperty("link")
    private String link;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("start_count")
    private String startCount;
    @JsonProperty("remains")
    private String remains;

    @JsonProperty("charge")
    public String getCharge() {
        return charge;
    }

    @JsonProperty("charge")
    public void setCharge(String charge) {
        this.charge = charge;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("start_count")
    public String getStartCount() {
        return startCount;
    }

    @JsonProperty("start_count")
    public void setStartCount(String startCount) {
        this.startCount = startCount;
    }

    @JsonProperty("remains")
    public String getRemains() {
        return remains;
    }

    @JsonProperty("remains")
    public void setRemains(String remains) {
        this.remains = remains;
    }

    public Double charge() {
        return Double.valueOf(getCharge());
    }

    @Override
    public String toString() {
        return "NakrutkaByOrderStatus{" +
                "charge='" + charge + '\'' +
                ", status='" + status + '\'' +
                ", link='" + link + '\'' +
                ", quantity='" + quantity + '\'' +
                ", startCount='" + startCount + '\'' +
                ", remains='" + remains + '\'' +
                '}';
    }
}
