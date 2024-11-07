package org.happiest.test;

import org.happiest.constants.LoggerConstants;
import org.happiest.controller.ForgotPasswordController;
import org.happiest.service.ForgotPasswordService;
import org.happiest.utility.ChangePasssword;
import org.happiest.utility.EmailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ForgotPasswordControllerTest {

    @InjectMocks
    private ForgotPasswordController forgotPasswordController;

    @Mock
    private ForgotPasswordService forgotPasswordService;

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordControllerTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifyEmail() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@example.com");
        String expectedResponse = "Verification link sent";

        when(forgotPasswordService.verifyEmail(emailRequest)).thenReturn(expectedResponse);

        ResponseEntity<String> responseEntity = forgotPasswordController.verifyEmail(emailRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void testVerifyOtp() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@example.com");
        String expectedResponse = "OTP verified successfully";

        when(forgotPasswordService.verifyOtp(emailRequest)).thenReturn(expectedResponse);

        ResponseEntity<String> responseEntity = forgotPasswordController.verifyOtp(emailRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void testChangePassword_Success() {
        ChangePasssword changePasssword = new ChangePasssword();
        changePasssword.setEmail("test@example.com");
        changePasssword.setPassword("newPassword123");
        changePasssword.setRepeatPassword("newPassword123");
        String expectedResponse = "Password changed successfully";

        when(forgotPasswordService.changePassword(changePasssword)).thenReturn(expectedResponse);

        ResponseEntity<String> responseEntity = forgotPasswordController.changePasswordHandler(changePasssword);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void testChangePassword_Failure() {
        ChangePasssword changePasssword = new ChangePasssword();
        changePasssword.setEmail("test@example.com");
        changePasssword.setPassword("newPassword123");
        changePasssword.setRepeatPassword("differentPassword");
        String expectedResponse = "Passwords do not match!";

        when(forgotPasswordService.changePassword(changePasssword)).thenReturn(expectedResponse);

        ResponseEntity<String> responseEntity = forgotPasswordController.changePasswordHandler(changePasssword);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
