package com.junsu.cyr.controller.auth;

import com.junsu.cyr.model.auth.PasswordResetRequest;
import com.junsu.cyr.model.email.EmailCodeRequest;
import com.junsu.cyr.model.email.EmailMatchRequest;
import com.junsu.cyr.response.success.SuccessResponse;
import com.junsu.cyr.service.auth.AuthService;
import com.junsu.cyr.service.auth.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/password/reset")
    public ResponseEntity<?> passwordReissue(@RequestBody PasswordResetRequest request) {
        authService.passwordReset(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok(SuccessResponse.success("Password reset successfully"));
    }
}