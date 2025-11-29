package com.junsu.cyr.flow.authentication.token;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.util.CookieUtil;
import com.junsu.cyr.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReissueAccessTokenFlow {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Transactional
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> cookie = CookieUtil.getCookie(request, "refreshToken");

        if (cookie.isEmpty()) {
            throw new BaseException(AuthExceptionCode.NO_CORRESPONDING_REFRESH_TOKEN);
        }

        String refreshToken = cookie.get().getValue();
        if (jwtTokenProvider.isValidToken(refreshToken)) {
            throw new BaseException(AuthExceptionCode.INVALID_REFRESH_TOKEN);
        }

        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        Integer userId = Integer.parseInt(claims.getSubject());
        User user = userService.getUserById(userId);

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        CookieUtil.addCookie(response, "accessToken", accessToken);
    }
}
