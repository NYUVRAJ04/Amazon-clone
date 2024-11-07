package org.happiest.test;

import org.happiest.exception.EmailSendingException;
import org.happiest.service.EmailService;
import org.happiest.utility.MailBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendSimpleMessage_Success() {
        // Create a MailBody object with test data
        MailBody mailBody = new MailBody("test@example.com", "Test Subject", "Test Message");

        // Call the method to test
        emailService.sendSimpleMessage(mailBody);

        // Verify that the JavaMailSender's send method was called once
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(mailBody.to());
        expectedMessage.setFrom("nyuvraj04@gmail.com");
        expectedMessage.setSubject(mailBody.subject());
        expectedMessage.setText(mailBody.text());

        verify(javaMailSender, times(1)).send(expectedMessage);
    }

    @Test
    void sendSimpleMessage_Failure() {
        // Create a MailBody object with test data
        MailBody mailBody = new MailBody("test@example.com", "Test Subject", "Test Message");

        // Simulate a MailException when trying to send an email
        doThrow(new MailException("Mail error") {}).when(javaMailSender).send(any(SimpleMailMessage.class));

        // Assert that EmailSendingException is thrown when sending the message
        EmailSendingException exception = assertThrows(EmailSendingException.class, () -> {
            emailService.sendSimpleMessage(mailBody);
        });

        assertEquals("Failed to send email to test@example.com", exception.getMessage());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
