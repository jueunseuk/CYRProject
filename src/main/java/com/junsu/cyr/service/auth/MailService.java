package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.users.Email;
import com.junsu.cyr.repository.EmailRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.EmailExceptionCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Transactional
    public void sendMail(String inputEmail) {
        String authCode = generateVerificationCode();

        emailRepository.findByEmail(inputEmail)
                .ifPresentOrElse(
                        existingEmail -> {
                            existingEmail.updateCode(authCode);
                            existingEmail.updateCreatedAt();
                            emailRepository.save(existingEmail);
                        },
                        () -> {
                            Email newEmail = Email.builder()
                                    .email(inputEmail)
                                    .code(authCode)
                                    .createdAt(LocalDateTime.now())
                                    .build();
                            emailRepository.save(newEmail);
                        }
                );

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(inputEmail);
            helper.setSubject("회원가입 인증 코드");
            helper.setText("인증 코드: " + authCode, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BaseException(EmailExceptionCode.FAILED_TO_SEND_CODE);
        }
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public void verifyCode(String email, String inputCode) {
        Email emailEntity = emailRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(EmailExceptionCode.NO_CORRESPONDING_EMAIL_FOUND));

        if(userRepository.findByEmail(email).isPresent()){
           throw new BaseException(EmailExceptionCode.ALREADY_EXIST_EMAIL);
        }

        if(ChronoUnit.SECONDS.between(emailEntity.getCreatedAt(), LocalDateTime.now()) >= 600) {
            throw new BaseException(EmailExceptionCode.EMAIL_AUTHENTICATION_TIMEOUT);
        }

        if(!emailEntity.getCode().equals(inputCode.trim())) {
            throw new BaseException(EmailExceptionCode.UNMATCHED_AUTHENTICATION_CODE);
        }
    }

    public void verifyCodeWithPassword(String email, String inputCode) {
        Email emailEntity = emailRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(EmailExceptionCode.NO_CORRESPONDING_EMAIL_FOUND));

        if(ChronoUnit.SECONDS.between(emailEntity.getCreatedAt(), LocalDateTime.now()) >= 600) {
            throw new BaseException(EmailExceptionCode.EMAIL_AUTHENTICATION_TIMEOUT);
        }

        if(!emailEntity.getCode().equals(inputCode.trim())) {
            throw new BaseException(EmailExceptionCode.UNMATCHED_AUTHENTICATION_CODE);
        }
    }
}
