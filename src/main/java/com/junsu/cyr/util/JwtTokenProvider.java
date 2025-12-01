package com.junsu.cyr.util;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.SignupResponse;
import com.junsu.cyr.service.user.UserNicknameSettingService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserNicknameSettingService userNicknameSettingService;
    @Value("${jwt.jwt-key}")
    private String jwtSecretKey;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateAccessToken(User user) {
        return createToken(user, accessTokenExpiration);
    }

    public String generateRefreshToken(User user) {
        return createToken(user, refreshTokenExpiration);
    }

    private String createToken(User user, long expirationTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public SignupResponse generateToken(User user, HttpServletResponse response) {
        String refreshToken = generateRefreshToken(user);
        String accessToken = generateAccessToken(user);

        CookieUtil.addCookie(response, "refreshToken", refreshToken);
        CookieUtil.addCookie(response, "accessToken", accessToken);

        return new SignupResponse(
                user.getUserId(),
                user.getProfileUrl(),
                user.getName(),
                user.getNickname(),
                user.getCreatedAt(),
                user.getRole(),
                userNicknameSettingService.getUserNicknameColor(user)
        );
    }
}
