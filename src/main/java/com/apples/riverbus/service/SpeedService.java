package com.apples.riverbus.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SpeedService {

    public int getSpeed() {
        Random random = new Random();
        return random.nextInt(100) + 20;
    }
}
