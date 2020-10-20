package org.ndrshrzg.bikerental.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Sessions")
public class Session {

    @Id
    private String sessionId;

    private Long userId;
    private String userName;

    private Long bikeId;
    private String bikeFrame;

    private Long sessionStart;
    private Long sessionEnd;
    private Long sessionDurationSeconds;

    public Session(String sessionId, Long userId, String userName, Long bikeId, String bikeFrame, Long sessionStart) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.userName = userName;
        this.bikeId = bikeId;
        this.bikeFrame = bikeFrame;
        this.sessionStart = sessionStart;
        this.sessionEnd = null;
        this.sessionDurationSeconds = null;
    }

    public Session() {
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public String getBikeFrame() {
        return bikeFrame;
    }

    public void setBikeFrame(String bikeFrame) {
        this.bikeFrame = bikeFrame;
    }

    public Long getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(Long sessionStart) {
        this.sessionStart = sessionStart;
    }

    public Long getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(Long sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public Long getSessionDurationSeconds() {
        return sessionDurationSeconds;
    }

    public void setSessionDurationSeconds(Long sessionDurationSeconds) {
        this.sessionDurationSeconds = sessionDurationSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return sessionId.equals(session.sessionId) &&
                Objects.equals(userId, session.userId) &&
                Objects.equals(bikeId, session.bikeId) &&
                Objects.equals(sessionStart, session.sessionStart) &&
                Objects.equals(sessionEnd, session.sessionEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId, bikeId, sessionStart, sessionEnd);
    }

    @Override
    public String toString() {
        return "{sessionId:" + sessionId + ",userName:'" + userName + ",bikeFrame:'" + bikeFrame + ",sessionStart:"
                + sessionStart + ",sessionEnd:" + sessionEnd + '}';
    }
}
