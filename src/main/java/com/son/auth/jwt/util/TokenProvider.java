package com.son.auth.jwt.util;

import com.son.auth.example.domain.User;
import com.son.auth.example.repository.UserRepository;
import com.son.auth.security.PrincipalDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.validity-in.access}")
    private long ACCESS_TOKEN_VALIDITY_IN_MILLISECONDS;
    @Value("${jwt.validity-in.refresh}")
    private long REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS;
    private Key key;
    private final String AUTHORITIES_KEY = "auth";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String USER_KEY = "userId";

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String userId) {
        long now = new Date().getTime();
        Date validity = new Date(now + this.ACCESS_TOKEN_VALIDITY_IN_MILLISECONDS);
        return Jwts.builder()
                .claim(AUTHORITIES_KEY, 1)
                .claim(USER_KEY, userId)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact()
                ;
    }

    public String createRefreshToken() {
        long now = new Date().getTime();
        Date validity = new Date(now + this.REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS);
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact()
                ;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String jwt) {
        return true;
    }

    public Claims parseClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private final UserRepository userRepository;

    public Authentication getAuthentication(String jwt) {
        Claims claims = parseClaims(jwt);

        User user = userRepository.findByUserId((String) claims.get(USER_KEY));

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(user.getUserRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(new PrincipalDetails(user), jwt, authorities);
    }
}
