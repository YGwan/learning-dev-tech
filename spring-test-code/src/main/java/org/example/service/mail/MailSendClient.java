package org.example.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {

    public boolean sendMail(String fromEmail, String toEmail, String title, String body) {
        log.info("메일 전송");
        System.out.println(fromEmail + " -> " + toEmail);
        System.out.println("title : " + title);
        System.out.println(body);
        return true;
    }

    public void test() {
        log.info("test");
    }
}
