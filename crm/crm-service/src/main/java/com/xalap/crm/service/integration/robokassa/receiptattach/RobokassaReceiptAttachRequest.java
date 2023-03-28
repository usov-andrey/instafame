
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.robokassa.receiptattach;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "merchantId",
        "id",
        "originId",
        "operation",
        "sno",
        "url",
        "total",
        "items",
        "client",
        "payments",
        "vats"
})
public class RobokassaReceiptAttachRequest {

    @JsonProperty("merchantId")
    private String merchantId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("originId")
    private String originId;
    @JsonProperty("operation")
    private String operation;
    @JsonProperty("sno")
    private String sno;
    @JsonProperty("url")
    private String url;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("items")
    private List<Item> items = null;
    @JsonProperty("client")
    private Client client;
    @JsonProperty("payments")
    private List<Payment> payments = null;
    @JsonProperty("vats")
    private List<Vat> vats = null;

    @JsonProperty("merchantId")
    public String getMerchantId() {
        return merchantId;
    }

    @JsonProperty("merchantId")
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("originId")
    public String getOriginId() {
        return originId;
    }

    @JsonProperty("originId")
    public void setOriginId(String originId) {
        this.originId = originId;
    }

    @JsonProperty("operation")
    public String getOperation() {
        return operation;
    }

    @JsonProperty("operation")
    public void setOperation(String operation) {
        this.operation = operation;
    }

    @JsonProperty("sno")
    public String getSno() {
        return sno;
    }

    @JsonProperty("sno")
    public void setSno(String sno) {
        this.sno = sno;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonProperty("client")
    public Client getClient() {
        return client;
    }

    @JsonProperty("client")
    public void setClient(Client client) {
        this.client = client;
    }

    @JsonProperty("payments")
    public List<Payment> getPayments() {
        return payments;
    }

    @JsonProperty("payments")
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @JsonProperty("vats")
    public List<Vat> getVats() {
        return vats;
    }

    @JsonProperty("vats")
    public void setVats(List<Vat> vats) {
        this.vats = vats;
    }

}
