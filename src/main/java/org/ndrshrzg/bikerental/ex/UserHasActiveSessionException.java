package org.ndrshrzg.bikerental.ex;

public class UserHasActiveSessionException extends RuntimeException {

    public UserHasActiveSessionException(Long id) {
        super("User with id " + id + " has an active session. Return bike first.");
    }

}
