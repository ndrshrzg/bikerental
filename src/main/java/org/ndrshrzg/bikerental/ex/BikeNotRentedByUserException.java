package org.ndrshrzg.bikerental.ex;

public class BikeNotRentedByUserException extends RuntimeException {

    public BikeNotRentedByUserException(Long userId, Long bikeId) {
        super("User " + userId + " cannot return bike " + bikeId + " as it is rented by someone else.");
    }

}
