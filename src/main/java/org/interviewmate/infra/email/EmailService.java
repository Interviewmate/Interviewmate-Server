package org.interviewmate.infra.email;

import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final String EMAIL_TITLE = "[InterviewMate] 인증 번호 입니다.";
    private final String EMAIL_CONTENT = "인증 번호 : ";

    private final JavaMailSender javaMailSender;

    public String authenticateEmail(String email) {

        Random random = new Random();
        String authenticationKey = String.valueOf(random.nextInt(88888) + 11111);

        sendAuthenticationEmail(email, authenticationKey);
        return authenticationKey;
    }

    private void sendAuthenticationEmail(String email, String authenticationKey) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email);
            helper.setSubject(EMAIL_TITLE);
            helper.setText(EMAIL_CONTENT + authenticationKey, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
