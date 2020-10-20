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
    CommandLineRunner initBikes(BikeRepository repository) {
        return args -> {
            logger.info("Adding Bike: " + repository.save(new Bike("Wilier Cento1Air", 50.123506f, 8.542040f, true)));
            logger.info("Adding Bike: " + repository.save(new Bike("Specialized Tarmac SL7", 50.119553f, 8.640179f, true)));
            logger.info("Adding Bike: " + repository.save(new Bike("Colnago C63", 50.119664f, 8.650221f, true)));
        };
    }

}
