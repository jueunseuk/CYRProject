package com.junsu.cyr.flow.authentication.login;

import com.junsu.cyr.domain.users.Method;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.authentication.oauth.NaverCallbackFlow;
import com.junsu.cyr.model.auth.NaverUserRequest;
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
public class NaverLoginOrSignupFlow {

    private final NaverCallbackFlow naverCallbackFlow;
    private final OAuthService oAuthService;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignupResponse naverLoginOrSignUp(NaverUserRequest request, HttpServletResponse response) {
        String accessToken = naverCallbackFlow.naverCallback(request.getCode(), request.getState());
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
            user = authService.createUserWithOAuth(userInfo);
        }

        return jwtTokenProvider.generateToken(user, response);
    }
}
