package com.katanemimena.backend.citizen;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CitizenConfig {

    @Bean
    CommandLineRunner commandLineRunner(CitizenRepository citizenRepository){
        return args -> {
            Citizen giwrgos=new Citizen(
                "giwrgos",
                "mymail",
                    "43049837",
                    LocalDate.parse("1997-11-05"),
                    "34907",
                "323"
            );

            Citizen mpampis=new Citizen(
                "mpampis",
                "notmymail",
                    "44356249837",
                    LocalDate.parse("1997-10-05"),
                    "349054",
                "363"
            );

            citizenRepository.saveAll(
                    List.of(giwrgos, mpampis));
        };
    }
}
