
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.datalayer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "purchase"
})
public class Ecommerce {

    @JsonProperty("purchase")
    private Purchase purchase;

    @JsonProperty("purchase")
    public Purchase getPurchase() {
        return purchase;
    }

    @JsonProperty("purchase")
    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

}
