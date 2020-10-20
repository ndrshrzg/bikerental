package org.ndrshrzg.bikerental.preload;

import org.ndrshrzg.bikerental.db.UserRepository;
import org.ndrshrzg.bikerental.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PreloadUsers {

    private static final Logger logger = LoggerFactory.getLogger(PreloadUsers.class);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    CommandLineRunner initUsers(UserRepository repository) {
        return args -> {
            logger.info("Adding User: " + repository.save(new User("Henry", passwordEncoder.encode("1234temp!"), 50.119504f, 8.638137f, false)));
            logger.info("Adding User: " + repository.save(new User("Hans", passwordEncoder.encode("temp1234!"), 50.119229f, 8.640020f, false)));
            logger.info("Adding User: " + repository.save(new User("Thomas", passwordEncoder.encode("!temp1234"), 50.120452f, 8.650507f, false)));
        };
    }

}
