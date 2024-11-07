package com.happiest.service;

import com.happiest.constants.EmailServiceConstants;
import com.happiest.exception.EmailSendingException;
import com.happiest.utility.MailBody;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(MailBody mailBody) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailBody.to());
            message.setFrom(EmailServiceConstants.FROM_EMAIL);
            message.setSubject(mailBody.subject());
            message.setText(mailBody.text());

            javaMailSender.send(message);
        } catch (MailException e) {
            throw new EmailSendingException("Failed to send email to " + mailBody.to(), e);
        }
    }
}
