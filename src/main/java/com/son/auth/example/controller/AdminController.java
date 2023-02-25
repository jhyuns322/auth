package com.son.auth.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/")
    public ResponseEntity<?> test() {
        log.info("test()");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
