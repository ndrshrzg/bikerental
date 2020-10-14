package org.ndrshrzg.bikerental.preload;

import org.ndrshrzg.bikerental.db.UserRepository;
import org.ndrshrzg.bikerental.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreloadUsers {

    private static final Logger logger = LoggerFactory.getLogger(PreloadUsers.class);

    @Bean
    CommandLineRunner initUsers(UserRepository repository){
        return args -> {
            logger.info("Adding User: " + repository.save(new User("Henry", 50.119504f, 8.638137f, false)));
            logger.info("Adding User: " + repository.save(new User("Hans", 50.119229f, 8.640020f, false)));
            logger.info("Adding User: " + repository.save(new User("Thomas", 50.120452f, 8.650507f, false)));
        };
    }

}
