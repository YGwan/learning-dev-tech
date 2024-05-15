package org.example.service.mail;

import org.example.domain.MailSendHistory;
import org.example.repository.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 단위테스트 - @Mock, @InjectMocks Mocking
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTest2 {

    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

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
