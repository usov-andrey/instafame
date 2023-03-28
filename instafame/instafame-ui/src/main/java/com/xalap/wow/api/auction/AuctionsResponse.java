
package com.xalap.wow.api.auction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "realms",
        "auctions"
})
public class AuctionsResponse {

    @JsonProperty("realms")
    private List<Realm> realms = null;
    @JsonProperty("auctions")
    private List<Auction> auctions = null;

    @JsonProperty("realms")
    public List<Realm> getRealms() {
        return realms;
    }

    @JsonProperty("realms")
    public void setRealms(List<Realm> realms) {
        this.realms = realms;
    }

    @JsonProperty("auctions")
    public List<Auction> getAuctions() {
        return auctions;
    }

    @JsonProperty("auctions")
    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    @Override
    public String toString() {
        return "AuctionsResponse{" +
                "realms=" + realms +
                ", auctions=" + auctions +
                '}';
    }
}
