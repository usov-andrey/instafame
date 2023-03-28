
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.datalayer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "actionField",
        "products"
})
public class Purchase {

    @JsonProperty("actionField")
    private ActionField actionField;
    @JsonProperty("products")
    private List<Product> products = null;

    @JsonProperty("actionField")
    public ActionField getActionField() {
        return actionField;
    }

    @JsonProperty("actionField")
    public void setActionField(ActionField actionField) {
        this.actionField = actionField;
    }

    @JsonProperty("products")
    public List<Product> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
