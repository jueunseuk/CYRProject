package com.junsu.cyr.flow.authentication.login;

import com.junsu.cyr.domain.users.Method;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.authentication.oauth.KakaoCallbackFlow;
import com.junsu.cyr.model.auth.KakaoUserRequest;
import com.junsu.cyr.model.auth.OAuthUserInfoRequest;
import com.junsu.cyr.model.auth.SignupResponse;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.auth.AuthService;
import com.junsu.cyr.service.auth.OAuthService;
import com.junsu.cyr.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoLoginOrSignupFlow {

    private final KakaoCallbackFlow kakaoCallbackFlow;
    private final OAuthService oAuthService;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignupResponse kakaoLoginOrSignUp(KakaoUserRequest request, HttpServletResponse response) {
        String accessToken = kakaoCallbackFlow.getKakaoAccessToken(request.getCode());
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
            user = authService.createUserWithOAuth(userInfo);
        }

        return jwtTokenProvider.generateToken(user, response);
    }
}
