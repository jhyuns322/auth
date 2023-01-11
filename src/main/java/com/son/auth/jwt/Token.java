package com.son.auth.jwt;

import lombok.Builder;
import lombok.Data;

@Data
public class Token {
    private String accessToken;
    private String refreshToken;

    @Builder
    Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
