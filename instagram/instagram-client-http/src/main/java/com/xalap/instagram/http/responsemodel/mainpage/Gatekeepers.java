
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.mainpage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bn",
        "sms",
        "ld",
        "pl"
})
public class Gatekeepers {

    @JsonProperty("bn")
    private Boolean bn;
    @JsonProperty("sms")
    private Boolean sms;
    @JsonProperty("ld")
    private Boolean ld;
    @JsonProperty("pl")
    private Boolean pl;

    @JsonProperty("bn")
    public Boolean getBn() {
        return bn;
    }

    @JsonProperty("bn")
    public void setBn(Boolean bn) {
        this.bn = bn;
    }

    @JsonProperty("sms")
    public Boolean getSms() {
        return sms;
    }

    @JsonProperty("sms")
    public void setSms(Boolean sms) {
        this.sms = sms;
    }

    @JsonProperty("ld")
    public Boolean getLd() {
        return ld;
    }

    @JsonProperty("ld")
    public void setLd(Boolean ld) {
        this.ld = ld;
    }

    @JsonProperty("pl")
    public Boolean getPl() {
        return pl;
    }

    @JsonProperty("pl")
    public void setPl(Boolean pl) {
        this.pl = pl;
    }

    @Override
    public String toString() {
        return "Gatekeepers{" +
                "bn=" + bn +
                ", sms=" + sms +
                ", ld=" + ld +
                ", pl=" + pl +
                '}';
    }
}
