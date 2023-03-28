
package com.xalap.wow.api.classic.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "r",
    "g",
    "b",
    "a"
})
@Generated("jsonschema2pojo")
public class Color__1 {

    @JsonProperty("r")
    private Integer r;
    @JsonProperty("g")
    private Integer g;
    @JsonProperty("b")
    private Integer b;
    @JsonProperty("a")
    private Integer a;

    @JsonProperty("r")
    public Integer getR() {
        return r;
    }

    @JsonProperty("r")
    public void setR(Integer r) {
        this.r = r;
    }

    @JsonProperty("g")
    public Integer getG() {
        return g;
    }

    @JsonProperty("g")
    public void setG(Integer g) {
        this.g = g;
    }

    @JsonProperty("b")
    public Integer getB() {
        return b;
    }

    @JsonProperty("b")
    public void setB(Integer b) {
        this.b = b;
    }

    @JsonProperty("a")
    public Integer getA() {
        return a;
    }

    @JsonProperty("a")
    public void setA(Integer a) {
        this.a = a;
    }

}
