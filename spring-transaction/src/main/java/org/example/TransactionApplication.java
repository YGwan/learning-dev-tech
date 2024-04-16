package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TransactionApplication.class);
        application.setAdditionalProfiles("dev");
        application.run(args);
    }
}