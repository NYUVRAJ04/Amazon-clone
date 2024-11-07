package org.happiest.service;

import jakarta.transaction.Transactional;
import org.happiest.model.ForgotPassword;
import org.happiest.model.Users;
import org.happiest.repository.ForgotPasswordRepository;
import org.happiest.repository.UserRepository;
import org.happiest.utility.EmailRequest;
import org.happiest.utility.ChangePasssword;
import org.happiest.utility.MailBody;
import org.happiest.exception.EmailVerificationException;
import org.happiest.exception.ExpiredOtpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class ForgotPasswordService {

    private final UserRepository userRepo;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ForgotPasswordService(UserRepository userRepo, ForgotPasswordRepository forgotPasswordRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public String verifyEmail(EmailRequest emailRequest) {
        String email = emailRequest.getEmail();
        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email!"));

        int otp = generateOtp();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot password request: " + otp)
                .subject("OTP for Forgot Password Request")
                .build();

        Optional<ForgotPassword> existingRecord = forgotPasswordRepository.findByUser(user);
        ForgotPassword fp;

        if (existingRecord.isPresent()) {
            fp = existingRecord.get();
            fp.setOtp(otp);
            fp.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000));
        } else {
            fp = ForgotPassword.builder()
                    .otp(otp)
                    .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                    .user(user)
                    .build();
        }

        try {
            emailService.sendSimpleMessage(mailBody);
        } catch (MailException e) {
            throw new EmailVerificationException("Failed to send OTP email", e);
        }

        forgotPasswordRepository.save(fp);

        return "Email sent for verification!";
    }

    public String verifyOtp(EmailRequest emailRequest) {
        String email = emailRequest.getEmail();
        Integer otp = emailRequest.getOtp();

        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        ForgotPassword forgotPasswordEntry = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + email));

        if (forgotPasswordEntry.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPasswordEntry.getFpid());
            throw new ExpiredOtpException("OTP has expired for email: " + email);
        }

        return "OTP Verified";
    }

    @Transactional
    public String changePassword(ChangePasssword changePassword) {
        String email = changePassword.getEmail();

        if (!Objects.equals(changePassword.getPassword(), changePassword.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        String encodedPassword = passwordEncoder.encode(changePassword.getPassword());
        userRepo.updatePassword(encodedPassword, email);

        return "Password has been changed!";
    }

    private Integer generateOtp() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
