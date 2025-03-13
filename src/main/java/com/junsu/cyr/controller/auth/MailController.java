package com.junsu.cyr.controller.auth;

import com.junsu.cyr.service.auth.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/email")
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/request")
    public String MailSend(String mail){
        return String.valueOf(mailService.sendMail(mail));
    }
}