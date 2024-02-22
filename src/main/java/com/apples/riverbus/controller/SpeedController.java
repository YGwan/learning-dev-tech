package com.apples.riverbus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeedController {

    @GetMapping("/api/hi")
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("hi");
    }
}
