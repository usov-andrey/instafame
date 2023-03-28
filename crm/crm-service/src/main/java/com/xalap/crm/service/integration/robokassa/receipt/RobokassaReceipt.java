
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.robokassa.receipt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sno",
        "items"
})
public class RobokassaReceipt {

    @JsonProperty("sno")
    private String sno;
    @JsonProperty("items")
    private List<RobokassaReceiptItem> items = null;

    @JsonProperty("sno")
    public String getSno() {
        return sno;
    }

    @JsonProperty("sno")
    public void setSno(String sno) {
        this.sno = sno;
    }

    @JsonProperty("items")
    public List<RobokassaReceiptItem> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<RobokassaReceiptItem> items) {
        this.items = items;
    }

}
