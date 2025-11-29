package com.junsu.cyr.flow.authentication.login;

import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.EmailLoginRequest;
import com.junsu.cyr.model.auth.SignupResponse;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailLoginFlow {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignupResponse emailLogin(EmailLoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        if (user.getStatus() == Status.INACTIVE || user.getStatus() == Status.DELETED) {
            throw new BaseException(AuthExceptionCode.ACCOUNT_NOT_ACTIVE);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(AuthExceptionCode.NO_CORRESPONDING_PASSWORD_VALUE);
        }

        if(user.getStatus() == Status.SECESSION) {
            user.updateStatus(Status.ACTIVE);
        }

        return jwtTokenProvider.generateToken(user, response);
    }
}
