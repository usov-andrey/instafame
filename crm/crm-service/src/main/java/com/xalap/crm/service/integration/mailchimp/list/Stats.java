
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.mailchimp.list;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "member_count",
        "unsubscribe_count",
        "cleaned_count",
        "member_count_since_send",
        "unsubscribe_count_since_send",
        "cleaned_count_since_send",
        "campaign_count",
        "campaign_last_sent",
        "merge_field_count",
        "avg_sub_rate",
        "avg_unsub_rate",
        "target_sub_rate",
        "open_rate",
        "click_rate",
        "last_sub_date",
        "last_unsub_date"
})
public class Stats {

    @JsonProperty("member_count")
    private Integer memberCount;
    @JsonProperty("unsubscribe_count")
    private Integer unsubscribeCount;
    @JsonProperty("cleaned_count")
    private Integer cleanedCount;
    @JsonProperty("member_count_since_send")
    private Integer memberCountSinceSend;
    @JsonProperty("unsubscribe_count_since_send")
    private Integer unsubscribeCountSinceSend;
    @JsonProperty("cleaned_count_since_send")
    private Integer cleanedCountSinceSend;
    @JsonProperty("campaign_count")
    private Integer campaignCount;
    @JsonProperty("campaign_last_sent")
    private String campaignLastSent;
    @JsonProperty("merge_field_count")
    private Integer mergeFieldCount;
    @JsonProperty("avg_sub_rate")
    private Integer avgSubRate;
    @JsonProperty("avg_unsub_rate")
    private Integer avgUnsubRate;
    @JsonProperty("target_sub_rate")
    private Integer targetSubRate;
    @JsonProperty("open_rate")
    private Integer openRate;
    @JsonProperty("click_rate")
    private Integer clickRate;
    @JsonProperty("last_sub_date")
    private String lastSubDate;
    @JsonProperty("last_unsub_date")
    private String lastUnsubDate;

    @JsonProperty("member_count")
    public Integer getMemberCount() {
        return memberCount;
    }

    @JsonProperty("member_count")
    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    @JsonProperty("unsubscribe_count")
    public Integer getUnsubscribeCount() {
        return unsubscribeCount;
    }

    @JsonProperty("unsubscribe_count")
    public void setUnsubscribeCount(Integer unsubscribeCount) {
        this.unsubscribeCount = unsubscribeCount;
    }

    @JsonProperty("cleaned_count")
    public Integer getCleanedCount() {
        return cleanedCount;
    }

    @JsonProperty("cleaned_count")
    public void setCleanedCount(Integer cleanedCount) {
        this.cleanedCount = cleanedCount;
    }

    @JsonProperty("member_count_since_send")
    public Integer getMemberCountSinceSend() {
        return memberCountSinceSend;
    }

    @JsonProperty("member_count_since_send")
    public void setMemberCountSinceSend(Integer memberCountSinceSend) {
        this.memberCountSinceSend = memberCountSinceSend;
    }

    @JsonProperty("unsubscribe_count_since_send")
    public Integer getUnsubscribeCountSinceSend() {
        return unsubscribeCountSinceSend;
    }

    @JsonProperty("unsubscribe_count_since_send")
    public void setUnsubscribeCountSinceSend(Integer unsubscribeCountSinceSend) {
        this.unsubscribeCountSinceSend = unsubscribeCountSinceSend;
    }

    @JsonProperty("cleaned_count_since_send")
    public Integer getCleanedCountSinceSend() {
        return cleanedCountSinceSend;
    }

    @JsonProperty("cleaned_count_since_send")
    public void setCleanedCountSinceSend(Integer cleanedCountSinceSend) {
        this.cleanedCountSinceSend = cleanedCountSinceSend;
    }

    @JsonProperty("campaign_count")
    public Integer getCampaignCount() {
        return campaignCount;
    }

    @JsonProperty("campaign_count")
    public void setCampaignCount(Integer campaignCount) {
        this.campaignCount = campaignCount;
    }

    @JsonProperty("campaign_last_sent")
    public String getCampaignLastSent() {
        return campaignLastSent;
    }

    @JsonProperty("campaign_last_sent")
    public void setCampaignLastSent(String campaignLastSent) {
        this.campaignLastSent = campaignLastSent;
    }

    @JsonProperty("merge_field_count")
    public Integer getMergeFieldCount() {
        return mergeFieldCount;
    }

    @JsonProperty("merge_field_count")
    public void setMergeFieldCount(Integer mergeFieldCount) {
        this.mergeFieldCount = mergeFieldCount;
    }

    @JsonProperty("avg_sub_rate")
    public Integer getAvgSubRate() {
        return avgSubRate;
    }

    @JsonProperty("avg_sub_rate")
    public void setAvgSubRate(Integer avgSubRate) {
        this.avgSubRate = avgSubRate;
    }

    @JsonProperty("avg_unsub_rate")
    public Integer getAvgUnsubRate() {
        return avgUnsubRate;
    }

    @JsonProperty("avg_unsub_rate")
    public void setAvgUnsubRate(Integer avgUnsubRate) {
        this.avgUnsubRate = avgUnsubRate;
    }

    @JsonProperty("target_sub_rate")
    public Integer getTargetSubRate() {
        return targetSubRate;
    }

    @JsonProperty("target_sub_rate")
    public void setTargetSubRate(Integer targetSubRate) {
        this.targetSubRate = targetSubRate;
    }

    @JsonProperty("open_rate")
    public Integer getOpenRate() {
        return openRate;
    }

    @JsonProperty("open_rate")
    public void setOpenRate(Integer openRate) {
        this.openRate = openRate;
    }

    @JsonProperty("click_rate")
    public Integer getClickRate() {
        return clickRate;
    }

    @JsonProperty("click_rate")
    public void setClickRate(Integer clickRate) {
        this.clickRate = clickRate;
    }

    @JsonProperty("last_sub_date")
    public String getLastSubDate() {
        return lastSubDate;
    }

    @JsonProperty("last_sub_date")
    public void setLastSubDate(String lastSubDate) {
        this.lastSubDate = lastSubDate;
    }

    @JsonProperty("last_unsub_date")
    public String getLastUnsubDate() {
        return lastUnsubDate;
    }

    @JsonProperty("last_unsub_date")
    public void setLastUnsubDate(String lastUnsubDate) {
        this.lastUnsubDate = lastUnsubDate;
    }

}
