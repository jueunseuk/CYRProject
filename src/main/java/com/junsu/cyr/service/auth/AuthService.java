package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.*;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.code.EmailExceptionCode;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.service.image.S3Service;
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
    private final PasswordEncoder passwordEncoder;
    private final OAuthService oAuthService;
    private final JwtTokenProvider jwtTokenProvider;
    private final S3Service s3Service;

    @Transactional
    public SignupResponse naverLoginOrSignUp(NaverUserRequest request, HttpServletResponse response) {
        String accessToken = oAuthService.getNaverAccessToken(request.getCode(), request.getState());
        OAuthUserInfoRequest userInfo = oAuthService.getUserInfoFromNaver(accessToken);

        Optional<User> userCheck = userRepository.findByEmail(userInfo.getEmail());

        User user;
        if (userCheck.isPresent()) {
            user = userCheck.get();

            if (user.getStatus() != Status.ACTIVE) {
                throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
            }
        } else {
            user = createUserWithOAuth(userInfo);
        }

        SignupResponse signupResponse = new SignupResponse(
                user.getUserId(),
                user.getProfileUrl(),
                user.getName(),
                user.getNickname(),
                user.getRole()
        );

        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        CookieUtil.addCookie(response, "refreshToken", newRefreshToken);
        CookieUtil.addCookie(response, "accessToken", newAccessToken);

        return signupResponse;
    }

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest, HttpServletResponse response) {
        Optional<User> check = userRepository.findByEmail(signupRequest.getEmail());

        if (check.isPresent()) {
            throw new BaseException(EmailExceptionCode.ALREADY_EXIST_EMAIL);
        }

        if (!isValidPassword(signupRequest.getPassword())) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }

        User user = createdUserWithEmail(signupRequest);

        try {
            if (signupRequest.getProfileImage() != null) {
                String profileUrl = s3Service.uploadFile(signupRequest.getProfileImage(), Type.PROFILE);
                user.updateProfileUrl(profileUrl);
                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        return generateTokensAndCreateResponse(user, response);
    }

    public User createdUserWithEmail(SignupRequest signupRequest) {
        User user = User.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .passwordUpdatedAt(LocalDateTime.now())
                .nickname(signupRequest.getNickname())
                .role(Role.GUEST)
                .status(Status.ACTIVE)
                .method(signupRequest.getMethod())
                .epxCnt(0L)
                .cheerCnt(0L)
                .warn(0)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithOAuth(OAuthUserInfoRequest userInfo) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .role(Role.GUEST)
                .profileUrl(userInfo.getProfileImageUrl())
                .status(Status.ACTIVE)
                .method(userInfo.getMethod())
                .epxCnt(0L)
                .cheerCnt(0L)
                .warn(0)
                .build();

        return userRepository.save(user);
    }

    public SignupResponse login(EmailLoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        if (user.getStatus() != Status.ACTIVE) {
            throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(AuthExceptionCode.NO_CORRESPONDING_PASSWORD_VALUE);
        }

        return generateTokensAndCreateResponse(user, response);
    }

    private SignupResponse generateTokensAndCreateResponse(User user, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        String accessToken = jwtTokenProvider.generateAccessToken(user);

        CookieUtil.addCookie(response, "refreshToken", refreshToken);
        CookieUtil.addCookie(response, "accessToken", accessToken);

        return  new SignupResponse(
                user.getUserId(),
                user.getProfileUrl(),
                user.getName(),
                user.getNickname(),
                user.getRole()
        );
    }

    @Transactional
    public void passwordReset(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(EmailExceptionCode.NO_CORRESPONDING_EMAIL_FOUND));

        if (user.getStatus() == Status.SECESSION) {
            throw new BaseException(AuthExceptionCode.ACCOUNT_ALREADY_DEACTIVATED);
        }

        if (!isValidPassword(password)) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }

        user.updatePassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Transactional
    public void resetAccessToken(HttpServletRequest request, HttpServletResponse response) {
        User user = getUserFromRefreshToken(request);
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        CookieUtil.addCookie(response, "accessToken", accessToken);
    }

    public void logout(HttpServletResponse response) {
        CookieUtil.deleteCookie(response, "refreshToken");
        CookieUtil.deleteCookie(response, "accessToken");
    }

    @Transactional
    public void secede(HttpServletResponse response, HttpServletRequest request) {
        getUserFromRefreshToken(request);
        CookieUtil.deleteCookie(response, "refreshToken");
        CookieUtil.deleteCookie(response, "accessToken");
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
