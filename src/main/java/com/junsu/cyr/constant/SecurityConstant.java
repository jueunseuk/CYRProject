package com.junsu.cyr.constant;

import java.util.List;

public class SecurityConstant {
    public static final List<String> ALLOWED_ORIGINS = List.of(
            "http://localhost:5173",
            "http://localhost:3000",
            "http://192.168.0.3:5173",
            "https://cyr-community.vercel.app"
    );

    public static final List<String> ALLOWED_METHODS = List.of(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
    );

    public static final List<String> ALLOWED_HEADERS = List.of(
            "Authorization", "Content-Type", "Accept", "Origin"
    );

    public static final List<String> PERMIT_ENDPOINTS = List.of(
            "/auth/naver",
            "/auth/signup",
            "/auth/login",
            "/auth/token/refresh",
            "/auth/email/verification",
            "/auth/email/verification/validation",
            "/auth/password",
            "/auth/naver/callback",
            "/auth/google/callback",
            "/auth/kakao/callback",
            "/posts/new",
            "/posts/popular",
            "/announcement/fixed",
            "/posts/list",
            "/cheer/total",
            "/gallery/random/*",
            "/attendance/today",
            "/attendance/statistic",
            "/calendar/monthly",
            "/calendar/request/all",
            "/calendar/before",
            "/calendar/after",
            "/statistic",
            "/ranking/summary",
            "/ws/**"
    );
}
