package org.example.service;

import org.example.logging.annotation.MyLogging;
import org.springframework.stereotype.Service;

@Service
public class AopLogTestService {

    @MyLogging
    public void loggingTest() {
        System.out.println("logging test");
    }
}
