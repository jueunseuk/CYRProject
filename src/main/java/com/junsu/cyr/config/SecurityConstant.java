package com.junsu.cyr.config;

import java.util.List;

public class SecurityConstant {
    public static final List<String> ALLOWED_ORIGINS = List.of(
            "http://localhost:5173",
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
            "/auth/token/access/reset",
            "/auth/email/request",
            "/auth/email/check",
            "/auth/password/reset",
            "/auth/naver/callback"
    );
}
