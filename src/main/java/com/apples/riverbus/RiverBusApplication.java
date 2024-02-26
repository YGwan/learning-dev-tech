package com.apples.riverbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RiverBusApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RiverBusApplication.class);
        application.setAdditionalProfiles("dev");
        application.run(args);
    }
}