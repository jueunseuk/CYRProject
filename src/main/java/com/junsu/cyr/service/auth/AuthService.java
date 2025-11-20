package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.Method;
import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.*;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.code.EmailExceptionCode;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.user.UserService;
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
    private final UserService userService;

    @Transactional
    public SignupResponse naverLoginOrSignUp(NaverUserRequest request, HttpServletResponse response) {
        String accessToken = oAuthService.getNaverAccessToken(request.getCode(), request.getState());
        OAuthUserInfoRequest userInfo = oAuthService.getUserInfo(accessToken, "https://openapi.naver.com/v1/nid/me", Method.NAVER);

        Optional<User> userCheck = userRepository.findByEmail(userInfo.getEmail());

        User user;
        if (userCheck.isPresent()) {
            user = userCheck.get();

            if (user.getStatus() != Status.ACTIVE) {
                throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
            }

            if(user.getMethod() != Method.NAVER) {
                throw new BaseException(AuthExceptionCode.DIFFERENT_LOGIN_METHOD);
            }
        } else {
            user = createUserWithOAuth(userInfo);
        }

        return generateTokensAndCreateResponse(user, response);
    }

    @Transactional
    public SignupResponse googleLoginOrSignUp(GoogleUserRequest request, HttpServletResponse response) {
        String accessToken = oAuthService.getGoogleAccessToken(request.getCode());
        OAuthUserInfoRequest userInfo = oAuthService.getUserInfo(accessToken, "https://www.googleapis.com/oauth2/v2/userinfo", Method.GOOGLE);

        Optional<User> userCheck = userRepository.findByEmail(userInfo.getEmail());

        User user;
        if (userCheck.isPresent()) {
            user = userCheck.get();

            if (user.getStatus() != Status.ACTIVE) {
                throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
            }

            if(user.getMethod() != Method.GOOGLE) {
                throw new BaseException(AuthExceptionCode.DIFFERENT_LOGIN_METHOD);
            }
        } else {
            user = createUserWithOAuth(userInfo);
        }

        return generateTokensAndCreateResponse(user, response);
    }

    @Transactional
    public SignupResponse kakaoLoginOrSignUp(KakaoUserRequest request, HttpServletResponse response) {
        String accessToken = oAuthService.getKakaoAccessToken(request.getCode());
        OAuthUserInfoRequest userInfo = oAuthService.getUserInfo(accessToken, "https://kapi.kakao.com/v2/user/me", Method.KAKAO);

        Optional<User> userCheck = userRepository.findByEmail(userInfo.getEmail());

        User user;
        if (userCheck.isPresent()) {
            user = userCheck.get();

            if (user.getStatus() != Status.ACTIVE) {
                throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
            }

            if(user.getMethod() != Method.KAKAO) {
                throw new BaseException(AuthExceptionCode.DIFFERENT_LOGIN_METHOD);
            }
        } else {
            user = createUserWithOAuth(userInfo);
        }

        return generateTokensAndCreateResponse(user, response);
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
                .nickname(signupRequest.getNickname() == null ? signupRequest.getName() : signupRequest.getNickname())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .passwordUpdatedAt(LocalDateTime.now())
                .nickname(signupRequest.getNickname())
                .role(Role.MEMBER)
                .status(Status.ACTIVE)
                .method(signupRequest.getMethod())
                .epxCnt(0L)
                .glass(0)
                .sand(0)
                .temperature(0)
                .cheerCnt(0L)
                .postCnt(0L)
                .commentCnt(0L)
                .imageCnt(0L)
                .empathyCnt(0L)
                .warn(0)
                .attendanceCnt(0)
                .consecutiveAttendanceCnt(0)
                .maxConsecutiveAttendanceCnt(0)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithOAuth(OAuthUserInfoRequest userInfo) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .nickname(userInfo.getName())
                .role(Role.MEMBER)
                .profileUrl(userInfo.getProfileImageUrl())
                .status(Status.ACTIVE)
                .method(userInfo.getMethod())
                .epxCnt(0L)
                .glass(0)
                .sand(0)
                .temperature(0)
                .postCnt(0L)
                .commentCnt(0L)
                .imageCnt(0L)
                .empathyCnt(0L)
                .cheerCnt(0L)
                .warn(0)
                .attendanceCnt(0)
                .consecutiveAttendanceCnt(0)
                .maxConsecutiveAttendanceCnt(0)
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
                user.getCreatedAt(),
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
    public void secede(Integer userId, HttpServletResponse response) {
        CookieUtil.deleteCookie(response, "refreshToken");
        CookieUtil.deleteCookie(response, "accessToken");

        User user = userService.getUserById(userId);

        if(!user.getStatus().equals(Status.ACTIVE)) {
            throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
        }

        user.updateToSecession();
    }

    private User getUserFromRefreshToken(HttpServletRequest request) {
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

        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));
    }

    public Boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.length() <= 20;
    }
}
