package com.junsu.cyr.controller.auth;

import com.junsu.cyr.service.auth.AuthService;
import com.junsu.cyr.service.auth.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/email/request")
    public String mailsend(String mail){
        return String.valueOf(mailService.sendMail(mail));
    }

    @ResponseBody
    @PostMapping("/email/check")
    public Boolean codeCheck(String code){
        return true;
    }
}