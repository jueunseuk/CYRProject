package com.junsu.cyr.service.auth;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    public boolean sendMail(String email) {
        String authCode = generateVerificationCode();

        redisTemplate.opsForValue().set("EMAIL_VERIFICATION:" + email, authCode, 10, TimeUnit.MINUTES);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("회원가입 인증 코드");
            helper.setText("인증 코드: " + authCode, true);
            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public boolean verifyCode(String email, String inputCode) {
        String storedCode = redisTemplate.opsForValue().get("EMAIL_VERIFICATION:" + email);
        return storedCode != null && storedCode.equals(inputCode);
    }
}
