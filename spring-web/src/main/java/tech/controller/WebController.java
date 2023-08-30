package tech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("my-page");
    }
}
