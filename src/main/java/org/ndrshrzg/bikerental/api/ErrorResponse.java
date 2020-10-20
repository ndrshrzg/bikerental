package org.ndrshrzg.bikerental.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("errorMessage")
    private String errorMessage;

    public ErrorResponse(HttpStatus status, String errorMessage) {
        this.statusCode = status.value();
        this.errorMessage = errorMessage;
    }

}
