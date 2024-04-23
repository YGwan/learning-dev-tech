package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class AopLogTestService {

    public void loggingTest() {
        System.out.println("logging test");
    }
}
