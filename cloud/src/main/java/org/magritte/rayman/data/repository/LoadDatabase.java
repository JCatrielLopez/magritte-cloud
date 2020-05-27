package org.magritte.rayman.data.repository;

import lombok.extern.slf4j.Slf4j;

import org.magritte.rayman.data.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new User("1234", "catriel", "lopez", "abcd1234")));
            log.info("Preloading " + repository.save(new User("dni2", "name2", "lastname2", "password2")));
        };
    }
}