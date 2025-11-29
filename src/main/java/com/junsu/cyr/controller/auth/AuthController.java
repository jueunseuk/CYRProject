package com.junsu.cyr.controller.auth;

import com.junsu.cyr.flow.authentication.login.EmailLoginFlow;
import com.junsu.cyr.flow.authentication.login.GoogleLoginOrSignupFlow;
import com.junsu.cyr.flow.authentication.login.KakaoLoginOrSignupFlow;
import com.junsu.cyr.flow.authentication.login.NaverLoginOrSignupFlow;
import com.junsu.cyr.flow.authentication.passwork.ResetPasswordFlow;
import com.junsu.cyr.flow.authentication.signup.EmailSignupFlow;
import com.junsu.cyr.flow.authentication.token.ReissueAccessTokenFlow;
import com.junsu.cyr.model.auth.*;
import com.junsu.cyr.model.email.EmailCodeRequest;
import com.junsu.cyr.model.email.EmailMatchRequest;
import com.junsu.cyr.response.success.SuccessResponse;
import com.junsu.cyr.service.auth.AuthService;
import com.junsu.cyr.service.auth.MailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;
    private final NaverLoginOrSignupFlow naverLoginOrSignup;
    private final GoogleLoginOrSignupFlow googleLoginOrSignupFlow;
    private final KakaoLoginOrSignupFlow kakaoLoginOrSignupFlow;
    private final EmailSignupFlow emailSignupFlow;
    private final EmailLoginFlow emailLoginFlow;
    private final ResetPasswordFlow resetPasswordFlow;
    private final ReissueAccessTokenFlow reissueAccessTokenFlow;

    @PostMapping("/naver/callback")
    public ResponseEntity<SignupResponse> naverLoginOrSignup(@RequestBody NaverUserRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = naverLoginOrSignup.naverLoginOrSignUp(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/google/callback")
    public ResponseEntity<SignupResponse> googleLoginOrSignup(@RequestBody GoogleUserRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = googleLoginOrSignupFlow.googleLoginOrSignUp(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/kakao/callback")
    public ResponseEntity<SignupResponse> kakaoLoginOrSignup(@RequestBody KakaoUserRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = kakaoLoginOrSignupFlow.kakaoLoginOrSignUp(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/email/verification")
    public ResponseEntity<?> mailSend(@RequestBody EmailCodeRequest request){
        mailService.sendMail(request.getEmail(), request.getPurpose());
        return ResponseEntity.ok(SuccessResponse.success("Success to request authentication code"));
    }

    @PostMapping("/email/verification/validation")
    public ResponseEntity<?> signupCodeCheck(@RequestBody EmailMatchRequest request) {
        mailService.verifyCode(request.getEmail(), request.getCode(), request.getPurpose());
        return ResponseEntity.ok(SuccessResponse.success("Matches with authentication code"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@ModelAttribute SignupRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = emailSignupFlow.emailSignup(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmailLoginRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = emailLoginFlow.emailLogin(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordResetRequest request) {
        resetPasswordFlow.resetPassword(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(SuccessResponse.success("Password reset successfully"));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> resetAccessToken(HttpServletRequest request, HttpServletResponse response) {
        reissueAccessTokenFlow.reissueAccessToken(request, response);
        return ResponseEntity.ok(SuccessResponse.success("Regenerate access token successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok(SuccessResponse.success("Logout successfully"));
    }

    @PatchMapping("/secession")
    public ResponseEntity<?> secession(@RequestAttribute Integer userId, HttpServletResponse response) {
        authService.secede(userId, response);
        return ResponseEntity.ok(SuccessResponse.success("Secession successfully"));
    }
}