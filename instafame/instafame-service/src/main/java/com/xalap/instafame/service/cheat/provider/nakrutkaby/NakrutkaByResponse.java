/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.nakrutkaby;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Усов Андрей
 * @since 12/01/2019
 */
public class NakrutkaByResponse {

    @JsonProperty("Error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "NakrutkaByResponse{" +
                "error='" + error + '\'' +
                '}';
    }
}
