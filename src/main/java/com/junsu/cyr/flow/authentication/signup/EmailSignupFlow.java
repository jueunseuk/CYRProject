package com.junsu.cyr.flow.authentication.signup;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.SignupRequest;
import com.junsu.cyr.model.auth.SignupResponse;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.code.EmailExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.auth.AuthService;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailSignupFlow {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final S3Service s3Service;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignupResponse emailSignup(SignupRequest signupRequest, HttpServletResponse response) {
        Optional<User> check = userRepository.findByEmail(signupRequest.getEmail());

        if (check.isPresent()) {
            throw new BaseException(EmailExceptionCode.ALREADY_EXIST_EMAIL);
        }

        User user = authService.createUserWithEmail(signupRequest);

        if (signupRequest.getProfileImage() != null) {
            String profileUrl = s3Service.uploadFile(signupRequest.getProfileImage(), Type.PROFILE);
            user.updateProfileUrl(profileUrl);
            userRepository.save(user);
        }

        return jwtTokenProvider.generateToken(user, response);
    }
}
