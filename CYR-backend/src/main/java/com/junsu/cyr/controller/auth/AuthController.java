package com.junsu.cyr.controller.auth;

import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.MailExceptionCode;
import com.junsu.cyr.response.success.SuccessResponse;
import com.junsu.cyr.service.auth.AuthService;
import com.junsu.cyr.service.auth.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/email/request")
    public ResponseEntity<?> mailSend(@RequestParam String email){
        String.valueOf(mailService.sendMail(email));
        return ResponseEntity.ok(SuccessResponse.success("Success to request authentication code"));
    }

    @ResponseBody
    @PostMapping("/email/check")
    public ResponseEntity<?> codeCheck(@RequestParam String email, @RequestParam String code) {
        if(mailService.verifyCode(email, code)){
            return ResponseEntity.ok(SuccessResponse.success("Matches with authentication code"));
        } else{
            throw new BaseException(MailExceptionCode.UNMATCHED_AUTHENTICATION_CODE);
        }
    }
}