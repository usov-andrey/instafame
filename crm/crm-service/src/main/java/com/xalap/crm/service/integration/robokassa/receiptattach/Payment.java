
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.robokassa.receiptattach;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "sum"
})
public class Payment {

    @JsonProperty("type")
    private Integer type;
    @JsonProperty("sum")
    private Integer sum;

    @JsonProperty("type")
    public Integer getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonProperty("sum")
    public Integer getSum() {
        return sum;
    }

    @JsonProperty("sum")
    public void setSum(Integer sum) {
        this.sum = sum;
    }

}
