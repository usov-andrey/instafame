
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
        "activity_counts",
        "config",
        "country_code",
        "language_code",
        "entry_data",
        "gatekeepers",
        "qe",
        "hostname",
        "display_properties_server_guess",
        "environment_switcher_visible_server_guess",
        "platform",
        "is_canary",
        "probably_has_app",
        "show_app_install"
})
public class MainPage {

    @JsonProperty("activity_counts")
    private ActivityCounts activityCounts;
    @JsonProperty("config")
    private Config config;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("language_code")
    private String languageCode;
    @JsonProperty("entry_data")
    private EntryData entryData;
    @JsonProperty("gatekeepers")
    private Gatekeepers gatekeepers;
    @JsonProperty("qe")
    private Qe qe;
    @JsonProperty("hostname")
    private String hostname;
    @JsonProperty("display_properties_server_guess")
    private DisplayPropertiesServerGuess displayPropertiesServerGuess;
    @JsonProperty("environment_switcher_visible_server_guess")
    private Boolean environmentSwitcherVisibleServerGuess;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("is_canary")
    private Boolean isCanary;
    @JsonProperty("probably_has_app")
    private Boolean probablyHasApp;
    @JsonProperty("show_app_install")
    private Boolean showAppInstall;

    @JsonProperty("activity_counts")
    public ActivityCounts getActivityCounts() {
        return activityCounts;
    }

    @JsonProperty("activity_counts")
    public void setActivityCounts(ActivityCounts activityCounts) {
        this.activityCounts = activityCounts;
    }

    @JsonProperty("config")
    public Config getConfig() {
        return config;
    }

    @JsonProperty("config")
    public void setConfig(Config config) {
        this.config = config;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("language_code")
    public String getLanguageCode() {
        return languageCode;
    }

    @JsonProperty("language_code")
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @JsonProperty("entry_data")
    public EntryData getEntryData() {
        return entryData;
    }

    @JsonProperty("entry_data")
    public void setEntryData(EntryData entryData) {
        this.entryData = entryData;
    }

    @JsonProperty("gatekeepers")
    public Gatekeepers getGatekeepers() {
        return gatekeepers;
    }

    @JsonProperty("gatekeepers")
    public void setGatekeepers(Gatekeepers gatekeepers) {
        this.gatekeepers = gatekeepers;
    }

    @JsonProperty("qe")
    public Qe getQe() {
        return qe;
    }

    @JsonProperty("qe")
    public void setQe(Qe qe) {
        this.qe = qe;
    }

    @JsonProperty("hostname")
    public String getHostname() {
        return hostname;
    }

    @JsonProperty("hostname")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @JsonProperty("display_properties_server_guess")
    public DisplayPropertiesServerGuess getDisplayPropertiesServerGuess() {
        return displayPropertiesServerGuess;
    }

    @JsonProperty("display_properties_server_guess")
    public void setDisplayPropertiesServerGuess(DisplayPropertiesServerGuess displayPropertiesServerGuess) {
        this.displayPropertiesServerGuess = displayPropertiesServerGuess;
    }

    @JsonProperty("environment_switcher_visible_server_guess")
    public Boolean getEnvironmentSwitcherVisibleServerGuess() {
        return environmentSwitcherVisibleServerGuess;
    }

    @JsonProperty("environment_switcher_visible_server_guess")
    public void setEnvironmentSwitcherVisibleServerGuess(Boolean environmentSwitcherVisibleServerGuess) {
        this.environmentSwitcherVisibleServerGuess = environmentSwitcherVisibleServerGuess;
    }

    @JsonProperty("platform")
    public String getPlatform() {
        return platform;
    }

    @JsonProperty("platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @JsonProperty("is_canary")
    public Boolean getIsCanary() {
        return isCanary;
    }

    @JsonProperty("is_canary")
    public void setIsCanary(Boolean isCanary) {
        this.isCanary = isCanary;
    }

    @JsonProperty("probably_has_app")
    public Boolean getProbablyHasApp() {
        return probablyHasApp;
    }

    @JsonProperty("probably_has_app")
    public void setProbablyHasApp(Boolean probablyHasApp) {
        this.probablyHasApp = probablyHasApp;
    }

    @JsonProperty("show_app_install")
    public Boolean getShowAppInstall() {
        return showAppInstall;
    }

    @JsonProperty("show_app_install")
    public void setShowAppInstall(Boolean showAppInstall) {
        this.showAppInstall = showAppInstall;
    }

    @Override
    public String toString() {
        return "MainPage{" +
                "activityCounts=" + activityCounts +
                ", config=" + config +
                ", countryCode='" + countryCode + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", entryData=" + entryData +
                ", gatekeepers=" + gatekeepers +
                ", qe=" + qe +
                ", hostname='" + hostname + '\'' +
                ", displayPropertiesServerGuess=" + displayPropertiesServerGuess +
                ", environmentSwitcherVisibleServerGuess=" + environmentSwitcherVisibleServerGuess +
                ", platform='" + platform + '\'' +
                ", isCanary=" + isCanary +
                ", probablyHasApp=" + probablyHasApp +
                ", showAppInstall=" + showAppInstall +
                '}';
    }
}
