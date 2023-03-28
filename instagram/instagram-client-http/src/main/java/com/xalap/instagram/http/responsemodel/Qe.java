
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ebd",
        "create",
        "disc",
        "feed",
        "gql",
        "su_universe",
        "us",
        "us_li",
        "nav",
        "nav_lo",
        "poe",
        "pm",
        "profile",
        "deact",
        "sidecar",
        "ufi",
        "ufi_loggedout",
        "video"
})
public class Qe {

    @JsonProperty("ebd")
    private Ebd ebd;
    @JsonProperty("create")
    private Create create;
    @JsonProperty("disc")
    private Disc disc;
    @JsonProperty("feed")
    private Feed feed;
    @JsonProperty("gql")
    private Gql gql;
    @JsonProperty("su_universe")
    private SuUniverse suUniverse;
    @JsonProperty("us")
    private Us us;
    @JsonProperty("us_li")
    private UsLi usLi;
    @JsonProperty("nav")
    private Nav nav;
    @JsonProperty("nav_lo")
    private NavLo navLo;
    @JsonProperty("poe")
    private Poe poe;
    @JsonProperty("pm")
    private Pm pm;
    @JsonProperty("profile")
    private Profile profile;
    @JsonProperty("deact")
    private Deact deact;
    @JsonProperty("sidecar")
    private Sidecar sidecar;
    @JsonProperty("ufi")
    private Ufi ufi;
    @JsonProperty("ufi_loggedout")
    private UfiLoggedout ufiLoggedout;
    @JsonProperty("video")
    private Video video;

    @JsonProperty("ebd")
    public Ebd getEbd() {
        return ebd;
    }

    @JsonProperty("ebd")
    public void setEbd(Ebd ebd) {
        this.ebd = ebd;
    }

    @JsonProperty("create")
    public Create getCreate() {
        return create;
    }

    @JsonProperty("create")
    public void setCreate(Create create) {
        this.create = create;
    }

    @JsonProperty("disc")
    public Disc getDisc() {
        return disc;
    }

    @JsonProperty("disc")
    public void setDisc(Disc disc) {
        this.disc = disc;
    }

    @JsonProperty("feed")
    public Feed getFeed() {
        return feed;
    }

    @JsonProperty("feed")
    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @JsonProperty("gql")
    public Gql getGql() {
        return gql;
    }

    @JsonProperty("gql")
    public void setGql(Gql gql) {
        this.gql = gql;
    }

    @JsonProperty("su_universe")
    public SuUniverse getSuUniverse() {
        return suUniverse;
    }

    @JsonProperty("su_universe")
    public void setSuUniverse(SuUniverse suUniverse) {
        this.suUniverse = suUniverse;
    }

    @JsonProperty("us")
    public Us getUs() {
        return us;
    }

    @JsonProperty("us")
    public void setUs(Us us) {
        this.us = us;
    }

    @JsonProperty("us_li")
    public UsLi getUsLi() {
        return usLi;
    }

    @JsonProperty("us_li")
    public void setUsLi(UsLi usLi) {
        this.usLi = usLi;
    }

    @JsonProperty("nav")
    public Nav getNav() {
        return nav;
    }

    @JsonProperty("nav")
    public void setNav(Nav nav) {
        this.nav = nav;
    }

    @JsonProperty("nav_lo")
    public NavLo getNavLo() {
        return navLo;
    }

    @JsonProperty("nav_lo")
    public void setNavLo(NavLo navLo) {
        this.navLo = navLo;
    }

    @JsonProperty("poe")
    public Poe getPoe() {
        return poe;
    }

    @JsonProperty("poe")
    public void setPoe(Poe poe) {
        this.poe = poe;
    }

    @JsonProperty("pm")
    public Pm getPm() {
        return pm;
    }

    @JsonProperty("pm")
    public void setPm(Pm pm) {
        this.pm = pm;
    }

    @JsonProperty("profile")
    public Profile getProfile() {
        return profile;
    }

    @JsonProperty("profile")
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @JsonProperty("deact")
    public Deact getDeact() {
        return deact;
    }

    @JsonProperty("deact")
    public void setDeact(Deact deact) {
        this.deact = deact;
    }

    @JsonProperty("sidecar")
    public Sidecar getSidecar() {
        return sidecar;
    }

    @JsonProperty("sidecar")
    public void setSidecar(Sidecar sidecar) {
        this.sidecar = sidecar;
    }

    @JsonProperty("ufi")
    public Ufi getUfi() {
        return ufi;
    }

    @JsonProperty("ufi")
    public void setUfi(Ufi ufi) {
        this.ufi = ufi;
    }

    @JsonProperty("ufi_loggedout")
    public UfiLoggedout getUfiLoggedout() {
        return ufiLoggedout;
    }

    @JsonProperty("ufi_loggedout")
    public void setUfiLoggedout(UfiLoggedout ufiLoggedout) {
        this.ufiLoggedout = ufiLoggedout;
    }

    @JsonProperty("video")
    public Video getVideo() {
        return video;
    }

    @JsonProperty("video")
    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
