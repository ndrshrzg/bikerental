package org.ndrshrzg.bikerental.api;

import org.ndrshrzg.bikerental.db.BikeRepository;
import org.ndrshrzg.bikerental.model.Bike;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BikeController {

    private final BikeRepository bikeRepository;

    BikeController(BikeRepository bikeRepository){
        this.bikeRepository = bikeRepository;
    }

    @GetMapping("/bikes")
    List<Bike> getBikes(){
        return bikeRepository.findAll();
    }

}
