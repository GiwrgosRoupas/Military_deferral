package com.katanemimena.backend.authorized;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class AuthorizedUserConfig {
    private  String pass="$2a$10$14d8sqUba9ljdKFXYYbINeFBgDF1nYpFfiKvXBqTneu6jaFrtqkCO";
    @Bean
    CommandLineRunner commandLineRunnerUser(AuthorizedUserRepository authorizedUserRepository){
        return args -> {
            AuthorizedUser admin= new AuthorizedUser(
                    "admin",
                     pass,
                    "ROLE_ADMIN",
                    LocalDateTime.now()
            );

            AuthorizedUser testSecretary = new AuthorizedUser(
                    "testSecretary",
                    pass,
                    "ROLE_SECRETARY",
                    LocalDateTime.now()
            );

            AuthorizedUser testOfficer = new AuthorizedUser(
                    "testOfficer",
                    pass,
                    "ROLE_OFFICER",
                    LocalDateTime.now()
            );

            authorizedUserRepository.saveAll(
                    List.of(admin, testSecretary, testOfficer)
            );
        };
    }
}
