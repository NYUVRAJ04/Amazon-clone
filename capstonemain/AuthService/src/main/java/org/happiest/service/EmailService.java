package org.happiest.service;


import org.happiest.constants.EmailServiceConstants;
import org.happiest.exception.EmailSendingException;
import org.happiest.utility.MailBody;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(MailBody mailBody) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailBody.to());
            message.setFrom(EmailServiceConstants.FROM_EMAIL); // Use constant for from email
            message.setSubject(mailBody.subject());
            message.setText(mailBody.text());

            javaMailSender.send(message);
        } catch (MailException e) {
            // Log the error (you could use a logger here)
            System.err.println("Failed to send email: " + e.getMessage());

            // Optionally, rethrow a custom exception
            throw new EmailSendingException(EmailServiceConstants.EMAIL_SENDING_ERROR_MESSAGE + mailBody.to(), e);
        }
    }
}
