
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pk",
        "username",
        "full_name",
        "is_private",
        "profile_pic_url",
        "profile_pic_id",
        "is_verified",
        "has_anonymous_profile_picture",
        "media_count",
        "geo_media_count",
        "follower_count",
        "following_count",
        "following_tag_count",
        "biography",
        "biography_with_entities",
        "external_url",
        "external_lynx_url",
        "has_biography_translation",
        "total_igtv_videos",
        "usertags_count",
        "is_favorite",
        "is_favorite_for_stories",
        "live_subscription_status",
        "has_recommend_accounts",
        "has_chaining",
        "hd_profile_pic_versions",
        "hd_profile_pic_url_info",
        "mutual_followers_count",
        "direct_messaging",
        "address_street",
        "business_contact_method",
        "category",
        "city_id",
        "city_name",
        "contact_phone_number",
        "is_call_to_action_enabled",
        "latitude",
        "longitude",
        "public_email",
        "public_phone_country_code",
        "public_phone_number",
        "zip",
        "instagram_location_id",
        "is_business",
        "account_type",
        "can_hide_category",
        "can_hide_public_contacts",
        "should_show_category",
        "should_show_public_contacts",
        "should_show_tabbed_inbox"
})
public class UserResponse {

    @JsonProperty("pk")
    private String pk;
    @JsonProperty("username")
    private String username;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    @JsonProperty("profile_pic_url")
    private String profilePicUrl;
    @JsonProperty("profile_pic_id")
    private String profilePicId;
    @JsonProperty("is_verified")
    private Boolean isVerified;
    @JsonProperty("has_anonymous_profile_picture")
    private Boolean hasAnonymousProfilePicture;
    @JsonProperty("media_count")
    private Integer mediaCount;
    @JsonProperty("geo_media_count")
    private Integer geoMediaCount;
    @JsonProperty("follower_count")
    private Integer followerCount;
    @JsonProperty("following_count")
    private Integer followingCount;
    @JsonProperty("following_tag_count")
    private Integer followingTagCount;
    @JsonProperty("biography")
    private String biography;
    @JsonProperty("biography_with_entities")
    private BiographyWithEntities biographyWithEntities;
    @JsonProperty("external_url")
    private String externalUrl;
    @JsonProperty("external_lynx_url")
    private String externalLynxUrl;
    @JsonProperty("has_biography_translation")
    private Boolean hasBiographyTranslation;
    @JsonProperty("total_igtv_videos")
    private Integer totalIgtvVideos;
    @JsonProperty("usertags_count")
    private Integer usertagsCount;
    @JsonProperty("is_favorite")
    private Boolean isFavorite;
    @JsonProperty("is_favorite_for_stories")
    private Boolean isFavoriteForStories;
    @JsonProperty("live_subscription_status")
    private String liveSubscriptionStatus;
    @JsonProperty("has_recommend_accounts")
    private Boolean hasRecommendAccounts;
    @JsonProperty("has_chaining")
    private Boolean hasChaining;
    @JsonProperty("hd_profile_pic_versions")
    private List<HdProfilePicVersion> hdProfilePicVersions = null;
    @JsonProperty("hd_profile_pic_url_info")
    private HdProfilePicUrlInfo hdProfilePicUrlInfo;
    @JsonProperty("mutual_followers_count")
    private Integer mutualFollowersCount;
    @JsonProperty("direct_messaging")
    private String directMessaging;
    @JsonProperty("address_street")
    private String addressStreet;
    @JsonProperty("business_contact_method")
    private String businessContactMethod;
    @JsonProperty("category")
    private String category;
    @JsonProperty("city_id")
    private String cityId;
    @JsonProperty("city_name")
    private String cityName;
    @JsonProperty("contact_phone_number")
    private String contactPhoneNumber;
    @JsonProperty("is_call_to_action_enabled")
    private Boolean isCallToActionEnabled;
    @JsonProperty("latitude")
    private Object latitude;
    @JsonProperty("longitude")
    private Object longitude;
    @JsonProperty("public_email")
    private String publicEmail;
    @JsonProperty("public_phone_country_code")
    private String publicPhoneCountryCode;
    @JsonProperty("public_phone_number")
    private String publicPhoneNumber;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("instagram_location_id")
    private String instagramLocationId;
    @JsonProperty("is_business")
    private Boolean isBusiness;
    @JsonProperty("account_type")
    private Integer accountType;
    @JsonProperty("can_hide_category")
    private Boolean canHideCategory;
    @JsonProperty("can_hide_public_contacts")
    private Boolean canHidePublicContacts;
    @JsonProperty("should_show_category")
    private Boolean shouldShowCategory;
    @JsonProperty("should_show_public_contacts")
    private Boolean shouldShowPublicContacts;
    @JsonProperty("should_show_tabbed_inbox")
    private Boolean shouldShowTabbedInbox;

    @JsonProperty("pk")
    public String getPk() {
        return pk;
    }

    @JsonProperty("pk")
    public void setPk(String pk) {
        this.pk = pk;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("is_private")
    public Boolean getIsPrivate() {
        return isPrivate;
    }

    @JsonProperty("is_private")
    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @JsonProperty("profile_pic_url")
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    @JsonProperty("profile_pic_url")
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    @JsonProperty("profile_pic_id")
    public String getProfilePicId() {
        return profilePicId;
    }

    @JsonProperty("profile_pic_id")
    public void setProfilePicId(String profilePicId) {
        this.profilePicId = profilePicId;
    }

    @JsonProperty("is_verified")
    public Boolean getIsVerified() {
        return isVerified;
    }

    @JsonProperty("is_verified")
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    @JsonProperty("has_anonymous_profile_picture")
    public Boolean getHasAnonymousProfilePicture() {
        return hasAnonymousProfilePicture;
    }

    @JsonProperty("has_anonymous_profile_picture")
    public void setHasAnonymousProfilePicture(Boolean hasAnonymousProfilePicture) {
        this.hasAnonymousProfilePicture = hasAnonymousProfilePicture;
    }

    @JsonProperty("media_count")
    public Integer getMediaCount() {
        return mediaCount;
    }

    @JsonProperty("media_count")
    public void setMediaCount(Integer mediaCount) {
        this.mediaCount = mediaCount;
    }

    @JsonProperty("geo_media_count")
    public Integer getGeoMediaCount() {
        return geoMediaCount;
    }

    @JsonProperty("geo_media_count")
    public void setGeoMediaCount(Integer geoMediaCount) {
        this.geoMediaCount = geoMediaCount;
    }

    @JsonProperty("follower_count")
    public Integer getFollowerCount() {
        return followerCount;
    }

    @JsonProperty("follower_count")
    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    @JsonProperty("following_count")
    public Integer getFollowingCount() {
        return followingCount;
    }

    @JsonProperty("following_count")
    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }

    @JsonProperty("following_tag_count")
    public Integer getFollowingTagCount() {
        return followingTagCount;
    }

    @JsonProperty("following_tag_count")
    public void setFollowingTagCount(Integer followingTagCount) {
        this.followingTagCount = followingTagCount;
    }

    @JsonProperty("biography")
    public String getBiography() {
        return biography;
    }

    @JsonProperty("biography")
    public void setBiography(String biography) {
        this.biography = biography;
    }

    @JsonProperty("biography_with_entities")
    public BiographyWithEntities getBiographyWithEntities() {
        return biographyWithEntities;
    }

    @JsonProperty("biography_with_entities")
    public void setBiographyWithEntities(BiographyWithEntities biographyWithEntities) {
        this.biographyWithEntities = biographyWithEntities;
    }

    @JsonProperty("external_url")
    public String getExternalUrl() {
        return externalUrl;
    }

    @JsonProperty("external_url")
    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    @JsonProperty("external_lynx_url")
    public String getExternalLynxUrl() {
        return externalLynxUrl;
    }

    @JsonProperty("external_lynx_url")
    public void setExternalLynxUrl(String externalLynxUrl) {
        this.externalLynxUrl = externalLynxUrl;
    }

    @JsonProperty("has_biography_translation")
    public Boolean getHasBiographyTranslation() {
        return hasBiographyTranslation;
    }

    @JsonProperty("has_biography_translation")
    public void setHasBiographyTranslation(Boolean hasBiographyTranslation) {
        this.hasBiographyTranslation = hasBiographyTranslation;
    }

    @JsonProperty("total_igtv_videos")
    public Integer getTotalIgtvVideos() {
        return totalIgtvVideos;
    }

    @JsonProperty("total_igtv_videos")
    public void setTotalIgtvVideos(Integer totalIgtvVideos) {
        this.totalIgtvVideos = totalIgtvVideos;
    }

    @JsonProperty("usertags_count")
    public Integer getUsertagsCount() {
        return usertagsCount;
    }

    @JsonProperty("usertags_count")
    public void setUsertagsCount(Integer usertagsCount) {
        this.usertagsCount = usertagsCount;
    }

    @JsonProperty("is_favorite")
    public Boolean getIsFavorite() {
        return isFavorite;
    }

    @JsonProperty("is_favorite")
    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @JsonProperty("is_favorite_for_stories")
    public Boolean getIsFavoriteForStories() {
        return isFavoriteForStories;
    }

    @JsonProperty("is_favorite_for_stories")
    public void setIsFavoriteForStories(Boolean isFavoriteForStories) {
        this.isFavoriteForStories = isFavoriteForStories;
    }

    @JsonProperty("live_subscription_status")
    public String getLiveSubscriptionStatus() {
        return liveSubscriptionStatus;
    }

    @JsonProperty("live_subscription_status")
    public void setLiveSubscriptionStatus(String liveSubscriptionStatus) {
        this.liveSubscriptionStatus = liveSubscriptionStatus;
    }

    @JsonProperty("has_recommend_accounts")
    public Boolean getHasRecommendAccounts() {
        return hasRecommendAccounts;
    }

    @JsonProperty("has_recommend_accounts")
    public void setHasRecommendAccounts(Boolean hasRecommendAccounts) {
        this.hasRecommendAccounts = hasRecommendAccounts;
    }

    @JsonProperty("has_chaining")
    public Boolean getHasChaining() {
        return hasChaining;
    }

    @JsonProperty("has_chaining")
    public void setHasChaining(Boolean hasChaining) {
        this.hasChaining = hasChaining;
    }

    @JsonProperty("hd_profile_pic_versions")
    public List<HdProfilePicVersion> getHdProfilePicVersions() {
        return hdProfilePicVersions;
    }

    @JsonProperty("hd_profile_pic_versions")
    public void setHdProfilePicVersions(List<HdProfilePicVersion> hdProfilePicVersions) {
        this.hdProfilePicVersions = hdProfilePicVersions;
    }

    @JsonProperty("hd_profile_pic_url_info")
    public HdProfilePicUrlInfo getHdProfilePicUrlInfo() {
        return hdProfilePicUrlInfo;
    }

    @JsonProperty("hd_profile_pic_url_info")
    public void setHdProfilePicUrlInfo(HdProfilePicUrlInfo hdProfilePicUrlInfo) {
        this.hdProfilePicUrlInfo = hdProfilePicUrlInfo;
    }

    @JsonProperty("mutual_followers_count")
    public Integer getMutualFollowersCount() {
        return mutualFollowersCount;
    }

    @JsonProperty("mutual_followers_count")
    public void setMutualFollowersCount(Integer mutualFollowersCount) {
        this.mutualFollowersCount = mutualFollowersCount;
    }

    @JsonProperty("direct_messaging")
    public String getDirectMessaging() {
        return directMessaging;
    }

    @JsonProperty("direct_messaging")
    public void setDirectMessaging(String directMessaging) {
        this.directMessaging = directMessaging;
    }

    @JsonProperty("address_street")
    public String getAddressStreet() {
        return addressStreet;
    }

    @JsonProperty("address_street")
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    @JsonProperty("business_contact_method")
    public String getBusinessContactMethod() {
        return businessContactMethod;
    }

    @JsonProperty("business_contact_method")
    public void setBusinessContactMethod(String businessContactMethod) {
        this.businessContactMethod = businessContactMethod;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("city_id")
    public String getCityId() {
        return cityId;
    }

    @JsonProperty("city_id")
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @JsonProperty("city_name")
    public String getCityName() {
        return cityName;
    }

    @JsonProperty("city_name")
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @JsonProperty("contact_phone_number")
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    @JsonProperty("contact_phone_number")
    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    @JsonProperty("is_call_to_action_enabled")
    public Boolean getIsCallToActionEnabled() {
        return isCallToActionEnabled;
    }

    @JsonProperty("is_call_to_action_enabled")
    public void setIsCallToActionEnabled(Boolean isCallToActionEnabled) {
        this.isCallToActionEnabled = isCallToActionEnabled;
    }

    @JsonProperty("latitude")
    public Object getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Object getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("public_email")
    public String getPublicEmail() {
        return publicEmail;
    }

    @JsonProperty("public_email")
    public void setPublicEmail(String publicEmail) {
        this.publicEmail = publicEmail;
    }

    @JsonProperty("public_phone_country_code")
    public String getPublicPhoneCountryCode() {
        return publicPhoneCountryCode;
    }

    @JsonProperty("public_phone_country_code")
    public void setPublicPhoneCountryCode(String publicPhoneCountryCode) {
        this.publicPhoneCountryCode = publicPhoneCountryCode;
    }

    @JsonProperty("public_phone_number")
    public String getPublicPhoneNumber() {
        return publicPhoneNumber;
    }

    @JsonProperty("public_phone_number")
    public void setPublicPhoneNumber(String publicPhoneNumber) {
        this.publicPhoneNumber = publicPhoneNumber;
    }

    @JsonProperty("zip")
    public String getZip() {
        return zip;
    }

    @JsonProperty("zip")
    public void setZip(String zip) {
        this.zip = zip;
    }

    @JsonProperty("instagram_location_id")
    public String getInstagramLocationId() {
        return instagramLocationId;
    }

    @JsonProperty("instagram_location_id")
    public void setInstagramLocationId(String instagramLocationId) {
        this.instagramLocationId = instagramLocationId;
    }

    @JsonProperty("is_business")
    public Boolean getIsBusiness() {
        return isBusiness;
    }

    @JsonProperty("is_business")
    public void setIsBusiness(Boolean isBusiness) {
        this.isBusiness = isBusiness;
    }

    @JsonProperty("account_type")
    public Integer getAccountType() {
        return accountType;
    }

    @JsonProperty("account_type")
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @JsonProperty("can_hide_category")
    public Boolean getCanHideCategory() {
        return canHideCategory;
    }

    @JsonProperty("can_hide_category")
    public void setCanHideCategory(Boolean canHideCategory) {
        this.canHideCategory = canHideCategory;
    }

    @JsonProperty("can_hide_public_contacts")
    public Boolean getCanHidePublicContacts() {
        return canHidePublicContacts;
    }

    @JsonProperty("can_hide_public_contacts")
    public void setCanHidePublicContacts(Boolean canHidePublicContacts) {
        this.canHidePublicContacts = canHidePublicContacts;
    }

    @JsonProperty("should_show_category")
    public Boolean getShouldShowCategory() {
        return shouldShowCategory;
    }

    @JsonProperty("should_show_category")
    public void setShouldShowCategory(Boolean shouldShowCategory) {
        this.shouldShowCategory = shouldShowCategory;
    }

    @JsonProperty("should_show_public_contacts")
    public Boolean getShouldShowPublicContacts() {
        return shouldShowPublicContacts;
    }

    @JsonProperty("should_show_public_contacts")
    public void setShouldShowPublicContacts(Boolean shouldShowPublicContacts) {
        this.shouldShowPublicContacts = shouldShowPublicContacts;
    }

    @JsonProperty("should_show_tabbed_inbox")
    public Boolean getShouldShowTabbedInbox() {
        return shouldShowTabbedInbox;
    }

    @JsonProperty("should_show_tabbed_inbox")
    public void setShouldShowTabbedInbox(Boolean shouldShowTabbedInbox) {
        this.shouldShowTabbedInbox = shouldShowTabbedInbox;
    }

}
