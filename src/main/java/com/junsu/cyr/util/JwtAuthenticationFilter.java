package com.junsu.cyr.util;

import com.junsu.cyr.config.SecurityConstant;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Optional<Cookie> cookie = cookieUtil.getCookie(request, "accessToken");

        if (cookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = cookie.get().getValue();

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            throw new AuthenticationCredentialsNotFoundException("not valid access token");
        }

        Claims claims = jwtTokenProvider.parseClaims(token);
        Integer userId = Integer.parseInt(claims.getSubject());

        request.setAttribute("userId", userId);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        if (user.getStatus() != Status.ACTIVE) {
            throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                List.of(new SimpleGrantedAuthority(claims.get("role", String.class)))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return SecurityConstant.PERMIT_ENDPOINTS.contains(path);
    }
}