package org.example.integration;

import org.example.service.mail.MailSendClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ServiceTestContext {

    @MockBean
    protected MailSendClient mailSendClient;
}
