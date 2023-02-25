package com.son.auth.example.service.impl;

import com.son.auth.example.dto.AuthDto;
import com.son.auth.example.service.AuthService;
import com.son.auth.jwt.dto.Token;
import com.son.auth.jwt.util.TokenProvider;
import com.son.auth.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Override
    public String authenticateUser(AuthDto req) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUserId(), req.getUserPw()));
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            return principalDetails.getUsername();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getLocalizedMessage());
        }
    }

    @Override
    public Token createTokens(String userId) {
        String accessToken = tokenProvider.createAccessToken(userId);
        String refreshToken = tokenProvider.createRefreshToken();

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
