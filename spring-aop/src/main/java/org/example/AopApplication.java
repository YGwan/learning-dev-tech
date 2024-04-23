package org.example;

import org.example.service.AopLogTestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(AopApplication.class, args);
        AopLogTestService aopLogTestService = ac.getBean(AopLogTestService.class);
        aopLogTestService.loggingTest();
    }
}
