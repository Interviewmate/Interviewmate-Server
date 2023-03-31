package org.interviewmate.infra.email;

import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.interviewmate.infra.redis.RedisService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final String EMAIL_TITLE = "[InterviewMate] 인증 번호 입니다.";
    private final String EMAIL_CONTENT = "인증 번호 : ";

    private final JavaMailSender mailSender;
    private final RedisService redisService;

    public String sendEmail(String toEmail) {

       if (!redisService.existData(toEmail)) {
           redisService.deleteData(toEmail);
       }

       String code = createAuthCode();

       try {
           MimeMessage message = createEmailForm(toEmail, code);
           mailSender.send(message);
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

        redisService.setDataExpire(toEmail, authCode, 60 * 5L);

        return message;

    }

    public Boolean verifyEmailCode(String email, String code) {
        String findCode = redisService.getData(email);
        if (findCode == null) {
            return false;
        }
        return findCode.equals(code);
    }

}
