package org.example.service.mail;

import lombok.RequiredArgsConstructor;
import org.example.domain.MailSendHistory;
import org.example.repository.MailSendHistoryRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {

    private static final String ADMIN_EMAIL = "test@email.com";

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String email, String subject, String content) {
        boolean result = mailSendClient.sendMail(fromEmail, email, subject, content);

        if (!result) {
            throw new IllegalArgumentException(subject + " 메일 전송 실패");
        }

        mailSendHistoryRepository.save(MailSendHistory.builder()
                .fromEmail(fromEmail)
                .toEmail(email)
                .subject(subject)
                .content(content)
                .build()
        );

        return true;
    }

    public boolean sendMail(String email, String subject, String content) {
        return sendMail(ADMIN_EMAIL, email, subject, content);
    }
}
