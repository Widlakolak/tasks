package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    private Mail mail;
    private Mail mailWithCc;

    @BeforeEach
    void setUp() {
        mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test message")
                .build();

        mailWithCc = Mail.builder()
                .mailTo("test@test.com")
                .mailToCc("cc@test.com")
                .subject("Test")
                .message("Test message")
                .build();
    }

    @Test
    public void shouldSendEmailWithCc() {
        //Given
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("test@test.com");
        mailMessage.setCc("cc@test.com");
        mailMessage.setSubject("Test");
        mailMessage.setText("Test message");

        //When
        simpleEmailService.send(mailWithCc);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    public void shouldSendEmail() {
        //Given
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("test@test.com");
        mailMessage.setSubject("Test");
        mailMessage.setText("Test message");

        //When
        simpleEmailService.send(mail);

        //Then
        verify((javaMailSender), times(1)).send(mailMessage);
    }
}