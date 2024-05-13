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

    public boolean sendMail(String fromEmail, String email, String title, String message) {
        boolean result = mailSendClient.sendMail(fromEmail, email, title, message);

        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(email)
                    .subject(title)
                    .content(message)
                    .build()
            );
            return true;
        }

        return false;
    }

    public boolean sendMail(String email, String title, String message) {
        return sendMail(ADMIN_EMAIL, email, title, message);
    }
}
