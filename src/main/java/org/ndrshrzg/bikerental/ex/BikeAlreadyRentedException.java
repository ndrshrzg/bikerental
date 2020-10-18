package org.ndrshrzg.bikerental.ex;

public class BikeAlreadyRentedException extends RuntimeException {

    public BikeAlreadyRentedException(Long id){
        super("Bike with id " + id + " is not free. Choose a different bike.");
    }

}
