package org.example.service.mail;

import org.example.domain.MailSendHistory;
import org.example.repository.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 단위테스트 - Mockito.mock 메서드 사용
 */
class MailServiceTest1 {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;
    private final MailService mailService;

    // 초기화
    {
        mailSendClient = Mockito.mock(MailSendClient.class);
        mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);
        mailService = new MailService(mailSendClient, mailSendHistoryRepository);
    }

    @DisplayName("메일 전송이 정상적으로 완료되면 true값이 반환된다.")
    @Test
    void sendMail() {
        // stubbing ( mock 객체에 원하는 행위를 강제한다. )
        when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        boolean result = mailService.sendMail("fromEmail", "email", "subject", "content");

        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

    @DisplayName("메일 전송이 실패하면 IllegalArgumentException 에러를 발생시킨다.")
    @Test
    void sendMailWhenFail() {
        when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(false);

        var subject = "subject";

        assertThatThrownBy(() -> mailService.sendMail("fromEmail", "email", subject, "content"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(subject + " 메일 전송 실패");
    }
}
