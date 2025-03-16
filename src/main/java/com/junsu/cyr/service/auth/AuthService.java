package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.AuthExceptionCode;
import com.junsu.cyr.response.exception.code.EmailExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void passwordReset(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(EmailExceptionCode.NO_CORRESPONDING_EMAIL_FOUND));

        if(password.isEmpty() || password == null || password.length() < 8) {
            throw new BaseException(AuthExceptionCode.INVALID_PASSWORD_VALUE);
        }

        user.updatePassword(passwordEncoder.encode(password));
    }
}
