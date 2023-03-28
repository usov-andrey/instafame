
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
        "ebd",
        "bc3l",
        "ccp",
        "create_upsell",
        "disc",
        "feed",
        "su_universe",
        "us",
        "us_li",
        "nav",
        "nav_lo",
        "profile",
        "deact",
        "sidecar",
        "video",
        "filters",
        "typeahead",
        "location_tag",
        "pw_link",
        "delta_defaults",
        "appsell",
        "profile_sensitivity",
        "save"
})
public class Qe {

    @JsonProperty("ebd")
    private Ebd ebd;
    @JsonProperty("bc3l")
    private Bc3l bc3l;
    @JsonProperty("ccp")
    private Ccp ccp;
    @JsonProperty("create_upsell")
    private CreateUpsell createUpsell;
    @JsonProperty("disc")
    private Disc disc;
    @JsonProperty("feed")
    private Feed feed;
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
    @JsonProperty("profile")
    private Profile profile;
    @JsonProperty("deact")
    private Deact deact;
    @JsonProperty("sidecar")
    private Sidecar sidecar;
    @JsonProperty("video")
    private Video video;
    @JsonProperty("filters")
    private Filters filters;
    @JsonProperty("typeahead")
    private Typeahead typeahead;
    @JsonProperty("location_tag")
    private LocationTag locationTag;
    @JsonProperty("pw_link")
    private PwLink pwLink;
    @JsonProperty("delta_defaults")
    private DeltaDefaults deltaDefaults;
    @JsonProperty("appsell")
    private Appsell appsell;
    @JsonProperty("profile_sensitivity")
    private ProfileSensitivity profileSensitivity;
    @JsonProperty("save")
    private Save save;

    @JsonProperty("ebd")
    public Ebd getEbd() {
        return ebd;
    }

    @JsonProperty("ebd")
    public void setEbd(Ebd ebd) {
        this.ebd = ebd;
    }

    @JsonProperty("bc3l")
    public Bc3l getBc3l() {
        return bc3l;
    }

    @JsonProperty("bc3l")
    public void setBc3l(Bc3l bc3l) {
        this.bc3l = bc3l;
    }

    @JsonProperty("ccp")
    public Ccp getCcp() {
        return ccp;
    }

    @JsonProperty("ccp")
    public void setCcp(Ccp ccp) {
        this.ccp = ccp;
    }

    @JsonProperty("create_upsell")
    public CreateUpsell getCreateUpsell() {
        return createUpsell;
    }

    @JsonProperty("create_upsell")
    public void setCreateUpsell(CreateUpsell createUpsell) {
        this.createUpsell = createUpsell;
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

    @JsonProperty("video")
    public Video getVideo() {
        return video;
    }

    @JsonProperty("video")
    public void setVideo(Video video) {
        this.video = video;
    }

    @JsonProperty("filters")
    public Filters getFilters() {
        return filters;
    }

    @JsonProperty("filters")
    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    @JsonProperty("typeahead")
    public Typeahead getTypeahead() {
        return typeahead;
    }

    @JsonProperty("typeahead")
    public void setTypeahead(Typeahead typeahead) {
        this.typeahead = typeahead;
    }

    @JsonProperty("location_tag")
    public LocationTag getLocationTag() {
        return locationTag;
    }

    @JsonProperty("location_tag")
    public void setLocationTag(LocationTag locationTag) {
        this.locationTag = locationTag;
    }

    @JsonProperty("pw_link")
    public PwLink getPwLink() {
        return pwLink;
    }

    @JsonProperty("pw_link")
    public void setPwLink(PwLink pwLink) {
        this.pwLink = pwLink;
    }

    @JsonProperty("delta_defaults")
    public DeltaDefaults getDeltaDefaults() {
        return deltaDefaults;
    }

    @JsonProperty("delta_defaults")
    public void setDeltaDefaults(DeltaDefaults deltaDefaults) {
        this.deltaDefaults = deltaDefaults;
    }

    @JsonProperty("appsell")
    public Appsell getAppsell() {
        return appsell;
    }

    @JsonProperty("appsell")
    public void setAppsell(Appsell appsell) {
        this.appsell = appsell;
    }

    @JsonProperty("profile_sensitivity")
    public ProfileSensitivity getProfileSensitivity() {
        return profileSensitivity;
    }

    @JsonProperty("profile_sensitivity")
    public void setProfileSensitivity(ProfileSensitivity profileSensitivity) {
        this.profileSensitivity = profileSensitivity;
    }

    @JsonProperty("save")
    public Save getSave() {
        return save;
    }

    @JsonProperty("save")
    public void setSave(Save save) {
        this.save = save;
    }

    @Override
    public String toString() {
        return "Qe{" +
                "ebd=" + ebd +
                ", bc3l=" + bc3l +
                ", ccp=" + ccp +
                ", createUpsell=" + createUpsell +
                ", disc=" + disc +
                ", feed=" + feed +
                ", suUniverse=" + suUniverse +
                ", us=" + us +
                ", usLi=" + usLi +
                ", nav=" + nav +
                ", navLo=" + navLo +
                ", profile=" + profile +
                ", deact=" + deact +
                ", sidecar=" + sidecar +
                ", video=" + video +
                ", filters=" + filters +
                ", typeahead=" + typeahead +
                ", locationTag=" + locationTag +
                ", pwLink=" + pwLink +
                ", deltaDefaults=" + deltaDefaults +
                ", appsell=" + appsell +
                ", profileSensitivity=" + profileSensitivity +
                ", save=" + save +
                '}';
    }
}
