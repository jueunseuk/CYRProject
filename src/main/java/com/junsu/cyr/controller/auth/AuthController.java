package com.junsu.cyr.controller.auth;

import com.junsu.cyr.domain.users.Method;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    @PostMapping("/naver/callback")
    public ResponseEntity<SignupResponse> naverLoginOrSignup(@RequestBody NaverUserRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = authService.naverLoginOrSignUp(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/google/callback")
    public ResponseEntity<SignupResponse> googleLoginOrSignup(@RequestBody GoogleUserRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = authService.googleLoginOrSignUp(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/kakao/callback")
    public ResponseEntity<SignupResponse> kakaoLoginOrSignup(@RequestBody KakaoUserRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = authService.kakaoLoginOrSignUp(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/email/request")
    public ResponseEntity<?> mailSend(@RequestBody EmailCodeRequest request){
        mailService.sendMail(request.getEmail());
        return ResponseEntity.ok(SuccessResponse.success("Success to request authentication code"));
    }

    @PostMapping("/email/check-signup")
    public ResponseEntity<?> signupCodeCheck(@RequestBody EmailMatchRequest request) {
        mailService.verifyCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(SuccessResponse.success("Matches with authentication code"));
    }

    @PostMapping("/email/check")
    public ResponseEntity<?> resetCodeCheck(@RequestBody EmailMatchRequest request) {
        mailService.verifyCodeWithPassword(request.getEmail(), request.getCode());
        return ResponseEntity.ok(SuccessResponse.success("Matches with authentication code"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam("email") String email,
                                    @RequestParam("name") String name,
                                    @RequestParam("password") String password,
                                    @RequestParam("nickname") String nickname,
                                    @RequestParam("method") Method method,
                                    @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                                    HttpServletResponse response) {
        SignupRequest request = new SignupRequest(method, name, email, password, nickname, profileImage);
        SignupResponse signupResponse = authService.signup(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmailLoginRequest request, HttpServletResponse response) {
        SignupResponse signupResponse = authService.login(request, response);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordResetRequest request) {
        authService.passwordReset(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(SuccessResponse.success("Password reset successfully"));
    }

    @PostMapping("/token/access/reset")
    public ResponseEntity<?> resetAccessToken(HttpServletRequest request, HttpServletResponse response) {
        authService.resetAccessToken(request, response);
        return ResponseEntity.ok(SuccessResponse.success("Regenerate access token successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok(SuccessResponse.success("Logout successfully"));
    }

    @PostMapping("/secession")
    public ResponseEntity<?> secession(HttpServletResponse response) {
        authService.secede(response);
        return ResponseEntity.ok(SuccessResponse.success("Secession successfully"));
    }
}