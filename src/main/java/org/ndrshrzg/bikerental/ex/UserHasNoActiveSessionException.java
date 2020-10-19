package org.ndrshrzg.bikerental.ex;

public class UserHasNoActiveSessionException extends RuntimeException {

    public UserHasNoActiveSessionException(Long id) {
        super("User with id " + id + " does not have an active session, no bike can be returned.");
    }

}
