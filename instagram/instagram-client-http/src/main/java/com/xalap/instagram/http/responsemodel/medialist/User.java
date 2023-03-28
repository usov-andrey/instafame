
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.medialist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "edge_owner_to_timeline_media"
})
public class User {

    @JsonProperty("edge_owner_to_timeline_media")
    private EdgeOwnerToTimelineMedia edgeOwnerToTimelineMedia;

    @JsonProperty("edge_owner_to_timeline_media")
    public EdgeOwnerToTimelineMedia getEdgeOwnerToTimelineMedia() {
        return edgeOwnerToTimelineMedia;
    }

    @JsonProperty("edge_owner_to_timeline_media")
    public void setEdgeOwnerToTimelineMedia(EdgeOwnerToTimelineMedia edgeOwnerToTimelineMedia) {
        this.edgeOwnerToTimelineMedia = edgeOwnerToTimelineMedia;
    }

}
