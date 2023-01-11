package com.son.auth.service;

import com.son.auth.dto.UserDto;
import com.son.auth.jwt.Token;

public interface AuthService {
    String authenticateUser(UserDto req);

    Token createTokens(String userId);
}
