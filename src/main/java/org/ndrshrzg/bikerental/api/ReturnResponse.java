package org.ndrshrzg.bikerental.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ndrshrzg.bikerental.model.Session;

public class ReturnResponse {

    @JsonProperty("message")
    private String message;
    @JsonProperty("bike_id")
    private Long bikeId;
    @JsonProperty("at_timestamp_milliseconds")
    private Long sessionEnd;
    @JsonProperty("session_duration_seconds")
    private Long sessionDuration;

    public ReturnResponse(Session session, Long timestamp) {
        this.message = "Successfully returned bike.";
        this.bikeId = session.getBikeId();
        this.sessionEnd = timestamp;
        this.sessionDuration = (timestamp - session.getSessionStart()) / 1000L;
    }


}
