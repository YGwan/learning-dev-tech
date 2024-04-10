package org.example.service;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class SpeedService {

    public int getSpeed() {
        Random random = new Random();
        return random.nextInt(100) + 20;
    }
}
