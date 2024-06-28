package com.example.elandweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ElandWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElandWebApplication.class, args);
    }

}
