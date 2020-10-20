package org.ndrshrzg.bikerental.ex;

public class BikeNotRentedException extends RuntimeException {

    public BikeNotRentedException(Long id) {
        super("Bike with id " + id + " is already free and cannot be returned");
    }
}
