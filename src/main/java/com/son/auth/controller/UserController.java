package com.son.auth.controller;

import com.son.auth.dto.UserDto;
import com.son.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody UserDto req) {
        log.info("addUser() req: {}", req.toString());

        userService.addUser(req);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> testApi() {
        log.info("testApi works!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
