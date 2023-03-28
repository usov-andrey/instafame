
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.direct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "text",
        "link_context",
        "client_context",
        "mutation_token"
})
public class Link_ {

    @JsonProperty("text")
    private String text;
    @JsonProperty("link_context")
    private LinkContext_ linkContext;
    @JsonProperty("client_context")
    private String clientContext;
    @JsonProperty("mutation_token")
    private String mutationToken;

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("link_context")
    public LinkContext_ getLinkContext() {
        return linkContext;
    }

    @JsonProperty("link_context")
    public void setLinkContext(LinkContext_ linkContext) {
        this.linkContext = linkContext;
    }

    @JsonProperty("client_context")
    public String getClientContext() {
        return clientContext;
    }

    @JsonProperty("client_context")
    public void setClientContext(String clientContext) {
        this.clientContext = clientContext;
    }

    @JsonProperty("mutation_token")
    public String getMutationToken() {
        return mutationToken;
    }

    @JsonProperty("mutation_token")
    public void setMutationToken(String mutationToken) {
        this.mutationToken = mutationToken;
    }

}
