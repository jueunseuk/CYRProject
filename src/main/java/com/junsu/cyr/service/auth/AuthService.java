package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.*;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public User createUserWithEmail(SignupRequest signupRequest) {
        if(signupRequest.getAuthenticated() == null || !signupRequest.getAuthenticated()) {
            throw new BaseException(AuthExceptionCode.NOT_AUTHENTICATED_USER);
        }
        if(signupRequest.getEmail() == null | signupRequest.getPassword() == null || signupRequest.getMethod() == null || signupRequest.getNickname() == null ) {
            throw new BaseException(AuthExceptionCode.INVALID_VALUE_INJECTION);
        }
        isValidPassword(signupRequest.getPassword());

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
        if(userInfo.getEmail() == null || userInfo.getName() == null || userInfo.getMethod() == null) {
            throw new BaseException(AuthExceptionCode.FAILED_TO_FETCH_USER_INFO);
        }

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

    public void isValidPassword(String password) {
        if(password == null || password.length() < 8 || password.length() > 20) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }

        char[] chars = password.toCharArray();
        int alphabet = 0;
        int number = 0;
        int symbol = 0;

        for(char c : chars) {
            switch(getCharacterType(c)) {
                case 1: alphabet++; break;
                case 2: number++; break;
                case 3: symbol++; break;
                default: throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
            }
        }

        if(alphabet < 1 || number < 1 || symbol < 1) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }
    }

    private Integer getCharacterType(char c) {
        if(Character.isLetter(c)) {
            return 1;
        } else if(Character.isDigit(c)) {
            return 2;
        } else if(SPECIAL_CHARS.contains(c)) {
            return 3;
        }

        return -1;
    }

    private static final Set<Character> SPECIAL_CHARS = Set.of(
            '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*',
            '+', ',', '-', '.', '/', ':', ';', '<', '=', '>',
            '?', '@', '[', '₩', ']', '^', '_', '`', '{', '|',
            '}', '~'
    );
}
