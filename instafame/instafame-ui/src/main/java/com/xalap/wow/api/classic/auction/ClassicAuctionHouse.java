
package com.xalap.wow.api.classic.auction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_links",
        "connected_realm",
        "auctions",
        "id",
        "name"
})
@Generated("jsonschema2pojo")
public class ClassicAuctionHouse {

    @JsonProperty("_links")
    private Links links;
    @JsonProperty("connected_realm")
    private ConnectedRealm connectedRealm;
    @JsonProperty("auctions")
    private List<ClassicAuction> auctions = null;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("_links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("connected_realm")
    public ConnectedRealm getConnectedRealm() {
        return connectedRealm;
    }

    @JsonProperty("connected_realm")
    public void setConnectedRealm(ConnectedRealm connectedRealm) {
        this.connectedRealm = connectedRealm;
    }

    @JsonProperty("auctions")
    public List<ClassicAuction> getAuctions() {
        return auctions;
    }

    @JsonProperty("auctions")
    public void setAuctions(List<ClassicAuction> auctions) {
        this.auctions = auctions;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

}
