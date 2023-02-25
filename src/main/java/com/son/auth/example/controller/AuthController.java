package com.son.auth.example.controller;

import com.son.auth.example.dto.AuthDto;
import com.son.auth.jwt.dto.Token;
import com.son.auth.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/")
    public ResponseEntity<?> authenticate(@RequestBody AuthDto req) {
        log.info("authenticate() req: {}", req.toString());

        String userId = authService.authenticateUser(req);
        Token token = authService.createTokens(userId);

        HashMap<String, Object> results = new HashMap<>();
        results.put("accessToken", token.getAccessToken());

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}
