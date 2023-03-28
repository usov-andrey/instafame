
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "media_type",
        "image_versions2",
        "original_width",
        "original_height",
        "pk",
        "carousel_parent_id",
        "can_see_insights_as_brand"
})
public class CarouselMedium {

    @JsonProperty("id")
    private String id;
    @JsonProperty("media_type")
    private Integer mediaType;
    @JsonProperty("image_versions2")
    private ImageVersions2 imageVersions2;
    @JsonProperty("original_width")
    private Integer originalWidth;
    @JsonProperty("original_height")
    private Integer originalHeight;
    @JsonProperty("pk")
    private String pk;
    @JsonProperty("carousel_parent_id")
    private String carouselParentId;
    @JsonProperty("can_see_insights_as_brand")
    private Boolean canSeeInsightsAsBrand;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("media_type")
    public Integer getMediaType() {
        return mediaType;
    }

    @JsonProperty("media_type")
    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    @JsonProperty("image_versions2")
    public ImageVersions2 getImageVersions2() {
        return imageVersions2;
    }

    @JsonProperty("image_versions2")
    public void setImageVersions2(ImageVersions2 imageVersions2) {
        this.imageVersions2 = imageVersions2;
    }

    @JsonProperty("original_width")
    public Integer getOriginalWidth() {
        return originalWidth;
    }

    @JsonProperty("original_width")
    public void setOriginalWidth(Integer originalWidth) {
        this.originalWidth = originalWidth;
    }

    @JsonProperty("original_height")
    public Integer getOriginalHeight() {
        return originalHeight;
    }

    @JsonProperty("original_height")
    public void setOriginalHeight(Integer originalHeight) {
        this.originalHeight = originalHeight;
    }

    @JsonProperty("pk")
    public String getPk() {
        return pk;
    }

    @JsonProperty("pk")
    public void setPk(String pk) {
        this.pk = pk;
    }

    @JsonProperty("carousel_parent_id")
    public String getCarouselParentId() {
        return carouselParentId;
    }

    @JsonProperty("carousel_parent_id")
    public void setCarouselParentId(String carouselParentId) {
        this.carouselParentId = carouselParentId;
    }

    @JsonProperty("can_see_insights_as_brand")
    public Boolean getCanSeeInsightsAsBrand() {
        return canSeeInsightsAsBrand;
    }

    @JsonProperty("can_see_insights_as_brand")
    public void setCanSeeInsightsAsBrand(Boolean canSeeInsightsAsBrand) {
        this.canSeeInsightsAsBrand = canSeeInsightsAsBrand;
    }

}
