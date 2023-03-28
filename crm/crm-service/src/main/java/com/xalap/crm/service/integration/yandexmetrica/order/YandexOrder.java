
/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "client_uniq_id",
        "client_type",
        "create_date_time",
        "update_date_time",
        "finish_date_time",
        "revenue",
        "order_status",
        "cost",
        "products",
        "attribute_values"
})
@Generated("jsonschema2pojo")
public class YandexOrder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("client_uniq_id")
    private String clientUniqId;
    @JsonProperty("client_type")
    private String clientType;
    @JsonProperty("create_date_time")
    private String createDateTime;
    @JsonProperty("update_date_time")
    private String updateDateTime;
    @JsonProperty("finish_date_time")
    private String finishDateTime;
    @JsonProperty("revenue")
    private Double revenue;
    @JsonProperty("order_status")
    private String orderStatus;
    @JsonProperty("cost")
    private Double cost;
    @JsonProperty("products")
    private Map<String, Integer> products;
    @JsonProperty("attribute_values")
    private AttributeValues attributeValues;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("client_uniq_id")
    public String getClientUniqId() {
        return clientUniqId;
    }

    @JsonProperty("client_uniq_id")
    public void setClientUniqId(String clientUniqId) {
        this.clientUniqId = clientUniqId;
    }

    @JsonProperty("client_type")
    public String getClientType() {
        return clientType;
    }

    @JsonProperty("client_type")
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @JsonProperty("create_date_time")
    public String getCreateDateTime() {
        return createDateTime;
    }

    @JsonProperty("create_date_time")
    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    @JsonProperty("update_date_time")
    public String getUpdateDateTime() {
        return updateDateTime;
    }

    @JsonProperty("update_date_time")
    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @JsonProperty("finish_date_time")
    public String getFinishDateTime() {
        return finishDateTime;
    }

    @JsonProperty("finish_date_time")
    public void setFinishDateTime(String finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    @JsonProperty("revenue")
    public Double getRevenue() {
        return revenue;
    }

    @JsonProperty("revenue")
    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    @JsonProperty("order_status")
    public String getOrderStatus() {
        return orderStatus;
    }

    @JsonProperty("order_status")
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @JsonProperty("cost")
    public Double getCost() {
        return cost;
    }

    @JsonProperty("cost")
    public void setCost(Double cost) {
        this.cost = cost;
    }

    @JsonProperty("products")
    public Map<String, Integer> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    @JsonProperty("attribute_values")
    public AttributeValues getAttributeValues() {
        return attributeValues;
    }

    @JsonProperty("attribute_values")
    public void setAttributeValues(AttributeValues attributeValues) {
        this.attributeValues = attributeValues;
    }

}
