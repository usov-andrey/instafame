
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.vkserfing.getorder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "parent_id",
        "name",
        "type",
        "insurance",
        "adult",
        "time",
        "status",
        "link",
        "targeting",
        "money",
        "users",
        "project"
})
public class VkSerfingGetOrderData {

    @JsonProperty("id")
    private String id;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("insurance")
    private String insurance;
    @JsonProperty("adult")
    private String adult;
    @JsonProperty("time")
    private String time;
    @JsonProperty("status")
    private String status;
    @JsonProperty("link")
    private String link;
    @JsonProperty("targeting")
    private Targeting targeting;
    @JsonProperty("money")
    private Money money;
    @JsonProperty("users")
    private Users users;
    @JsonProperty("project")
    private Object project;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("parent_id")
    public String getParentId() {
        return parentId;
    }

    @JsonProperty("parent_id")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("insurance")
    public String getInsurance() {
        return insurance;
    }

    @JsonProperty("insurance")
    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    @JsonProperty("adult")
    public String getAdult() {
        return adult;
    }

    @JsonProperty("adult")
    public void setAdult(String adult) {
        this.adult = adult;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
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

    @JsonProperty("targeting")
    public Targeting getTargeting() {
        return targeting;
    }

    @JsonProperty("targeting")
    public void setTargeting(Targeting targeting) {
        this.targeting = targeting;
    }

    @JsonProperty("money")
    public Money getMoney() {
        return money;
    }

    @JsonProperty("money")
    public void setMoney(Money money) {
        this.money = money;
    }

    @JsonProperty("users")
    public Users getUsers() {
        return users;
    }

    @JsonProperty("users")
    public void setUsers(Users users) {
        this.users = users;
    }

    @JsonProperty("project")
    public Object getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(Object project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "VkSerfingGetOrderData{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", insurance='" + insurance + '\'' +
                ", adult='" + adult + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                ", link='" + link + '\'' +
                ", targeting=" + targeting +
                ", money=" + money +
                ", users=" + users +
                ", project=" + project +
                '}';
    }
}
