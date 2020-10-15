package org.ndrshrzg.bikerental.ex;

public class BikeNotFoundException extends RuntimeException {

    public BikeNotFoundException(Long id){
        super("Bike with id " + id + " could not be found.");
    }

}
