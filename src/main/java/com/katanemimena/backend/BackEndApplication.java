package com.katanemimena.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndApplication {

    public static void main(String[] args) {SpringApplication.run(BackEndApplication.class, args);}
    @Value("${client.url}")
    public static  String clientUrl ;
    @Value("${server.url}")
    public static  String serverUrl ;
}
