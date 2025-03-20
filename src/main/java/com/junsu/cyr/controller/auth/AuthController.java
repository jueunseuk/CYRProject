package com.junsu.cyr.controller.auth;

import com.junsu.cyr.domain.users.Method;
import com.junsu.cyr.model.auth.EmailLoginRequest;
import com.junsu.cyr.model.auth.PasswordResetRequest;
import com.junsu.cyr.model.auth.SignupRequest;
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

    @PostMapping("/email/request")
    public ResponseEntity<?> mailSend(@RequestBody EmailCodeRequest request){
        mailService.sendMail(request.getEmail());
        return ResponseEntity.ok(SuccessResponse.success("Success to request authentication code"));
    }

    @PostMapping("/email/check")
    public ResponseEntity<?> codeCheck(@RequestBody EmailMatchRequest request) {
        mailService.verifyCode(request.getEmail(), request.getCode());
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

        return authService.signup(request, response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmailLoginRequest request, HttpServletResponse response) {
        authService.login(request, response);
        return ResponseEntity.ok(SuccessResponse.success("login successful"));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordResetRequest request) {
        authService.passwordReset(request.getEmail(), request.getNewPassword());
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
    public ResponseEntity<?> secession(HttpServletRequest request, HttpServletResponse response) {
        authService.secede(response, request);
        return ResponseEntity.ok(SuccessResponse.success("Secession successfully"));
    }
}