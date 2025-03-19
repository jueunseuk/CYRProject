package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.EmailLoginRequest;
import com.junsu.cyr.model.auth.SignupRequest;
import com.junsu.cyr.model.auth.SignupResponse;
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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SignupResponse> signup(SignupRequest signupRequest, HttpServletResponse response) {
        Optional<User> check = userRepository.findByEmail(signupRequest.getEmail());

        if(check.isPresent()) {
            throw new BaseException(EmailExceptionCode.ALREADY_EXIST_EMAIL);
        }

        if(!isValidPassword(signupRequest.getPassword())) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }

        User user;
        switch (signupRequest.getMethod()) {
            case EMAIL -> user = createdUserWithEmail(signupRequest);
            case NAVER, GOOGLE, KAKAO -> user = createUserWithOAuth(signupRequest);
            default -> throw new BaseException(AuthExceptionCode.INVALID_LOGIN_METHOD);
        }
        userRepository.save(user);

        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        String accessToken = jwtTokenProvider.generateAccessToken(user);

        CookieUtil.addCookie(response, "refreshToken", refreshToken);

        response.setHeader("Authorization", "Bearer "+accessToken);

        SignupResponse signupResponse = new SignupResponse(
                user.getUserId(),
                user.getName(),
                user.getNickname(),
                accessToken,
                user.getRole()
        );

        return ResponseEntity.ok(signupResponse);
    }

    public User createdUserWithEmail(SignupRequest signupRequest) {
        User user = User.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .passwordUpdatedAt(LocalDateTime.now())
                .profileUrl(signupRequest.getProfileUrl())
                .nickname(signupRequest.getNickname())
                .role(Role.MEMBER)
                .status(Status.ACTIVE)
                .method(signupRequest.getMethod())
                .epxCnt(0L)
                .cheerCnt(0L)
                .warn(0)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithOAuth(SignupRequest signupRequest) {
        User user = User.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .profileUrl(signupRequest.getProfileUrl())
                .nickname(signupRequest.getNickname())
                .role(Role.MEMBER)
                .status(Status.ACTIVE)
                .method(signupRequest.getMethod())
                .epxCnt(0L)
                .cheerCnt(0L)
                .warn(0)
                .build();

        return userRepository.save(user);
    }

    public void login(EmailLoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        if(user.getStatus() != Status.ACTIVE) {
            throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
        }

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(AuthExceptionCode.NO_CORRESPONDING_PASSWORD_VALUE);
        }

        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        String accessToken = jwtTokenProvider.generateAccessToken(user);

        CookieUtil.addCookie(response, "refreshToken", refreshToken);

        response.setHeader("Authorization", "Bearer "+accessToken);
    }

    @Transactional
    public void passwordReset(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(EmailExceptionCode.NO_CORRESPONDING_EMAIL_FOUND));

        if (user.getStatus() == Status.SECESSION) {
            throw new BaseException(AuthExceptionCode.ACCOUNT_ALREADY_DEACTIVATED);
        }

        if(!isValidPassword(password)) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }

        user.updatePassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Transactional
    public void resetAccessToken(HttpServletRequest request, HttpServletResponse response) {
        User user = getUserFromRefreshToken(request);
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        response.setHeader("Authorization", "Bearer " + accessToken);
    }

    public void logout(HttpServletResponse response) {
        CookieUtil.deleteCookie(response, "refreshToken");
    }

    @Transactional
    public void secede(HttpServletResponse response, HttpServletRequest request) {
        getUserFromRefreshToken(request);
        CookieUtil.deleteCookie(response, "refreshToken");
    }

    private User getUserFromRefreshToken(HttpServletRequest request) {
        Optional<Cookie> cookie = CookieUtil.getCookie(request, "refreshToken");

        if (cookie.isEmpty()) {
            throw new BaseException(AuthExceptionCode.NO_CORRESPONDING_REFRESH_TOKEN);
        }

        String refreshToken = cookie.get().getValue();
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BaseException(AuthExceptionCode.INVALID_REFRESH_TOKEN);
        }

        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        Integer userId = Integer.parseInt(claims.getSubject());

        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));
    }

    public Boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.length() <= 20;
    }
}
