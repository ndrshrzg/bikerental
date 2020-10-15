package org.ndrshrzg.bikerental.api;

import org.ndrshrzg.bikerental.db.BikeRepository;
import org.ndrshrzg.bikerental.ex.BikeNotFoundException;
import org.ndrshrzg.bikerental.model.Bike;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/bikes/{bikeId}")
    Bike getBikeByBikeId(@PathVariable Long bikeId){
        return bikeRepository.findById(bikeId).orElseThrow(() -> new BikeNotFoundException(bikeId));
    }

}
