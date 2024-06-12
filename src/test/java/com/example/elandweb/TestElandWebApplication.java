package com.example.elandweb;

import org.springframework.boot.SpringApplication;

public class TestElandWebApplication {

    public static void main(String[] args) {
        SpringApplication.from(ElandWebApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
