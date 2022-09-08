package com.katanemimena.backend.form;

import com.katanemimena.backend.authorized.AuthorizedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class FormConfig {


    @Autowired
    FormRepository formRepository;
        @Bean
        CommandLineRunner commandLineRunnerForms(AuthorizedUserRepository authorizedUserRepository){
            return args -> {
                Form giwrgos= new Form(
                        69L,"giwrgos", "weoihewoiw@dffd.com"
                );

                Form mpampis= new Form(
                        420L,"mpampis", "mpampinos@gr.com"
                );


                formRepository.saveAll(
                        List.of(giwrgos, mpampis)
                );
            };
        }

}
