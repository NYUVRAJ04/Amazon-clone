package org.happiest.test;

import jakarta.transaction.Transactional;
import org.happiest.exception.EmailVerificationException;
import org.happiest.exception.ExpiredOtpException;
import org.happiest.model.ForgotPassword;
import org.happiest.model.Users;
import org.happiest.repository.ForgotPasswordRepository;
import org.happiest.repository.UserRepository;
import org.happiest.service.EmailService;
import org.happiest.service.ForgotPasswordService;
import org.happiest.utility.EmailRequest;
import org.happiest.utility.ChangePasssword;
import org.happiest.utility.MailBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ForgotPasswordServiceTest {

    @InjectMocks
    private ForgotPasswordService forgotPasswordService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private ForgotPasswordRepository forgotPasswordRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Users testUser;
    private ForgotPassword testForgotPassword;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a test user and forgot password entry
        testUser = new Users();
        testUser.setEmail("test@example.com");

        testForgotPassword = new ForgotPassword();
        testForgotPassword.setOtp(123456);
        testForgotPassword.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000)); // Set expiration time
        testForgotPassword.setUser(testUser);
    }

    @Test
    void verifyEmail_UserExists_EmailSent() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@example.com");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(forgotPasswordRepository.findByUser(testUser)).thenReturn(Optional.empty());
        doNothing().when(emailService).sendSimpleMessage(any(MailBody.class)); // Change here

        String response = forgotPasswordService.verifyEmail(emailRequest);

        assertEquals("Email sent for verification!", response);
        verify(forgotPasswordRepository).save(any(ForgotPassword.class));
    }

    @Test
    void verifyEmail_UserNotFound_ExceptionThrown() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("invalid@example.com");

        when(userRepo.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            forgotPasswordService.verifyEmail(emailRequest);
        });

        assertEquals("Please provide a valid email!", exception.getMessage());
    }

    @Test
    void verifyEmail_EmailSendingFails_ExceptionThrown() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@example.com");

        // Mock the behavior of the user repository
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(forgotPasswordRepository.findByUser(testUser)).thenReturn(Optional.empty());

        // Use doThrow() for void methods to simulate an exception being thrown
        doThrow(new MailException("Mail error") {}).when(emailService).sendSimpleMessage(any(MailBody.class));

        // Assert that the expected exception is thrown
        Exception exception = assertThrows(EmailVerificationException.class, () -> {
            forgotPasswordService.verifyEmail(emailRequest);
        });

        // Check that the exception message is correct
        assertEquals("Failed to send OTP email", exception.getMessage());
    }


    @Test
    void verifyOtp_SuccessfulVerification() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@example.com");
        emailRequest.setOtp(123456);

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(forgotPasswordRepository.findByOtpAndUser(123456, testUser)).thenReturn(Optional.of(testForgotPassword));

        String response = forgotPasswordService.verifyOtp(emailRequest);
        assertEquals("OTP Verified", response);
    }

    @Test
    void verifyOtp_InvalidOtp_ExceptionThrown() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@example.com");
        emailRequest.setOtp(999999); // Invalid OTP

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(forgotPasswordRepository.findByOtpAndUser(999999, testUser)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            forgotPasswordService.verifyOtp(emailRequest);
        });

        assertEquals("Invalid OTP for email: test@example.com", exception.getMessage());
    }

    @Test
    void verifyOtp_ExpiredOtp_ExceptionThrown() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@example.com");
        emailRequest.setOtp(123456);

        // Set expiration time to the past
        testForgotPassword.setExpirationTime(new Date(System.currentTimeMillis() - 1000));

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(forgotPasswordRepository.findByOtpAndUser(123456, testUser)).thenReturn(Optional.of(testForgotPassword));

        Exception exception = assertThrows(ExpiredOtpException.class, () -> {
            forgotPasswordService.verifyOtp(emailRequest);
        });

        assertEquals("OTP has expired for email: test@example.com", exception.getMessage());
    }

    @Test
    void changePassword_SuccessfulChange() {
        ChangePasssword changePassword = new ChangePasssword();
        changePassword.setEmail("test@example.com");
        changePassword.setPassword("newPassword123");
        changePassword.setRepeatPassword("newPassword123");

        // Mock the behavior of the password encoder
        when(passwordEncoder.encode("newPassword123")).thenReturn("encodedPassword");

        // Call the method to test
        forgotPasswordService.changePassword(changePassword);

        // Verify that the encode and updatePassword methods were called
        verify(passwordEncoder).encode("newPassword123");
        verify(userRepo).updatePassword("encodedPassword", "test@example.com");
    }

    @Test
    void changePassword_PasswordsDoNotMatch_ExceptionThrown() {
        ChangePasssword changePassword = new ChangePasssword();
        changePassword.setEmail("test@example.com");
        changePassword.setPassword("password123");
        changePassword.setRepeatPassword("differentPassword");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            forgotPasswordService.changePassword(changePassword);
        });

        assertEquals("Passwords do not match!", exception.getMessage());
    }


}
