package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.SignupRequest;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.code.EmailExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.util.CookieUtil;
import com.junsu.cyr.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest signupRequest, HttpServletResponse response) {
        Optional<User> check = userRepository.findByEmail(signupRequest.getEmail());

        if(check.isPresent()) {
            throw new BaseException(EmailExceptionCode.ALREADY_EXIST_EMAIL);
        }

        if(!isValidPassword(signupRequest.getPassword())) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }

        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .passwordUpdatedAt(LocalDateTime.now())
                .name(signupRequest.getName())
                .profileUrl(signupRequest.getProfileUrl())
                .nickname(signupRequest.getNickname())
                .age(signupRequest.getAge())
                .gender(signupRequest.getGender())
                .build();

        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(user);

        CookieUtil.addCookie(response, "refreshToken", refreshToken);

        response.setHeader("Authorization", "Bearer "+accessToken);
    }

    @Transactional
    public void passwordReset(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(EmailExceptionCode.NO_CORRESPONDING_EMAIL_FOUND));

        if(!isValidPassword(password)) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }

        user.updatePassword(passwordEncoder.encode(password));
    }

    @Transactional
    public void resetAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> cookie = CookieUtil.getCookie(request, "refreshToken");

        if(cookie.isEmpty()) {
            throw new BaseException(AuthExceptionCode.NO_CORRESPONDING_REFRESH_TOKEN);
        }

        String refreshToken = cookie.get().getValue();
        if(!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BaseException(AuthExceptionCode.INVALID_REFRESH_TOKEN);
        }

        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        Integer userId = Integer.parseInt(claims.getSubject());

        User user = userRepository.findByUserId(userId)
                        .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER_ID));

        String accessToken = jwtTokenProvider.generateAccessToken(user);

        response.setHeader("Authorization", "Bearer "+accessToken);
    }

    public Boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.length() <= 20;
    }
}
