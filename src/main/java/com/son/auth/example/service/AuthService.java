package com.son.auth.example.service;

import com.son.auth.example.dto.AuthDto;
import com.son.auth.jwt.dto.Token;

public interface AuthService {
    String authenticateUser(AuthDto req);

    Token createTokens(String userId);
}
