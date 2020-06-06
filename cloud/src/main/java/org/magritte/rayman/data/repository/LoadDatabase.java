package org.magritte.rayman.data.repository;

import lombok.extern.slf4j.Slf4j;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;

@Configuration
@Slf4j
public class LoadDatabase {

//    @Bean
//    public CommandLineRunner initDatabase(UserRepository repository) {
//        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//        return args -> {
//            log.info("Preloading " + repository.save(
//                    new Patient("1234",
//                            "catriel",
//                            "lopez",
//                            "abcd1234",
//                            "catriellopez@gmail.com",
//                            date,
//                            'F',
//                            157,
//                            55.0f)
//                    )
//            );
//            log.info("Preloading " + repository.save(
//                    new Medic("dni2",
//                            "name2",
//                            "lastname2",
//                            "password2",
//                            "name2lastname2@gmail.com",
//                            "specialization",
//                            12312)
//                    )
//            );
//        };
//    }
}