package org.ndrshrzg.bikerental.preload;

import org.ndrshrzg.bikerental.db.BikeRepository;
import org.ndrshrzg.bikerental.model.Bike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreloadBikes {

    private static final Logger logger = LoggerFactory.getLogger(PreloadBikes.class);

    @Bean
    CommandLineRunner initBikes(BikeRepository repository){
        return args -> {
            logger.info("Adding Bike: " + repository.save(new Bike("Wilier Cento1Air", true)));
            logger.info("Adding Bike: " + repository.save(new Bike("Specialized Tarmac SL7", true)));
            logger.info("Adding Bike: " + repository.save(new Bike("Colnago C63", false)));
        };
    }

}
