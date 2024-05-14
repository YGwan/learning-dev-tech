package org.example.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * 통합테스트 - springboot 사용
 */
@SpringBootTest
class MailServiceTest4 {

    @Autowired
    private MailService mailService;

    @MockBean
    private MailSendClient mailSendClient;

    @DisplayName("메일 전송이 정상적으로 완료되면 true값이 반환된다.")
    @Test
    void sendMail() {
        when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        boolean result = mailService.sendMail("fromEmail", "email", "subject", "content");

        assertThat(result).isTrue();
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
