package org.interviewmate.infra.email.service;

import static org.interviewmate.global.error.ErrorCode.DUPLICATE_EMAIL;

import java.util.Objects;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.user.exception.UserException;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.infra.redis.repository.RedisEmailRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final String EMAIL_TITLE = "[InterviewMate] 인증 번호 입니다.";
    private final String EMAIL_CONTENT = "인증 번호 : ";

    private final JavaMailSender mailSender;
    private final RedisEmailRepository redisService;
    private final UserRepository userRepository;

    public String sendEmail(String toEmail) {

        User user = userRepository.findByEmail(toEmail).orElse(null);

        if(Objects.nonNull(user)) {
            throw new UserException(DUPLICATE_EMAIL);
        }

       if (redisService.isExist(toEmail)) {
           redisService.delete(toEmail);
       }

       String code = createAuthCode();

       try {
           MimeMessage message = createEmailForm(toEmail, code);
           mailSender.send(message);
           redisService.saveWithExpirationDate(toEmail, code, 60 * 5L);
       } catch (MessagingException e) {
           e.printStackTrace();
       }

       return "이메일을 확인하세요.";

    }

    private String createAuthCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(88888) + 11111);
    }

    private MimeMessage createEmailForm(String toEmail, String authCode) throws MessagingException{

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(toEmail);
        helper.setSubject(EMAIL_TITLE);
        helper.setText(EMAIL_CONTENT + authCode, true);

        return message;

    }

    public Boolean verifyEmailCode(String email, String code) {

        String findCode = redisService.findByKey(email);
        return Objects.equals(findCode, code);

    }

}
