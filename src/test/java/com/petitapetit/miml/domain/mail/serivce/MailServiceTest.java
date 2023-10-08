package com.petitapetit.miml.domain.mail.serivce;

import com.petitapetit.miml.domain.notification.entity.Notification;
import com.petitapetit.miml.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

class MailServiceTest extends ServiceTest {
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private TextMailService textMailService;

    @Test
    void testSendEmail() {
        // given
        Notification notification = mock(Notification.class);
        when(notification.getUserEmail()).thenReturn("test@email.com");
        when(notification.makeSubject()).thenReturn("Test Subject");
        when(notification.makeText()).thenReturn("Test Message");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // when
        textMailService.sendEmail(notification);

        // then
        verify(javaMailSender, times(1)).createMimeMessage();
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }
}