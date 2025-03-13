package com.junsu.cyr.service.auth;

import com.junsu.cyr.domain.users.Email;
import com.junsu.cyr.repository.EmailRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.EmailExceptionCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Transactional
    public void sendMail(String inputEmail) {
        String authCode = generateVerificationCode();

        emailRepository.findByEmail(inputEmail)
                .ifPresentOrElse(
                        existingEmail -> {
                            existingEmail.updateCode(authCode);
                            emailRepository.save(existingEmail);
                        },
                        () -> {
                            Email newEmail = Email.builder()
                                    .email(inputEmail)
                                    .code(authCode)
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

    public Boolean verifyCode(String email, String inputCode) {
        Optional<Email> find = emailRepository.findByEmail(email);
        return find.get().getCode().equals(inputCode);
    }
}
