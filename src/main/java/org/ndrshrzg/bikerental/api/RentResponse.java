package org.ndrshrzg.bikerental.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ndrshrzg.bikerental.model.Session;

public class RentResponse {

    @JsonProperty("message")
    private String message;
    @JsonProperty("bike_id")
    private Long bikeId;
    @JsonProperty("at_timestamp_milliseconds")
    private Long sessionStart;

    public RentResponse(Session session) {
        this.message = "Successfully rented bike.";
        this.bikeId = session.getBikeId();
        this.sessionStart = session.getSessionStart();
    }
}
