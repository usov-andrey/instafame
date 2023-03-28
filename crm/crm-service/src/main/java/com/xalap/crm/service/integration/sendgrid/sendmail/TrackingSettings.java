/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.sendgrid.sendmail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Усов Андрей
 * @since 08/02/2019
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TrackingSettings {
    /*
    @JsonProperty("click_tracking") private ClickTrackingSetting clickTrackingSetting;
    @JsonProperty("open_tracking") private OpenTrackingSetting openTrackingSetting;
    @JsonProperty("subscription_tracking") private SubscriptionTrackingSetting subscriptionTrackingSetting;*/
    @JsonProperty("ganalytics")
    private GoogleAnalyticsSetting googleAnalyticsSetting;

    @JsonProperty("ganalytics")
    public GoogleAnalyticsSetting getGoogleAnalyticsSetting() {
        return googleAnalyticsSetting;
    }

    public void setGoogleAnalyticsSetting(GoogleAnalyticsSetting googleAnalyticsSetting) {
        this.googleAnalyticsSetting = googleAnalyticsSetting;
    }

}
