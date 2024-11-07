package org.happiest.controller;


import org.happiest.constants.LoggerConstants;
import org.happiest.service.ForgotPasswordService;
import org.happiest.utility.ChangePasssword;
import org.happiest.utility.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailRequest emailRequest) {
        logger.info(LoggerConstants.LOG_VERIFYING_EMAIL, emailRequest.getEmail());
        String response = forgotPasswordService.verifyEmail(emailRequest);
        logger.info(LoggerConstants.LOG_EMAIL_VERIFICATION_RESPONSE, response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestBody EmailRequest emailRequest) {
        logger.info(LoggerConstants.LOG_VERIFYING_OTP, emailRequest.getEmail());
        String response = forgotPasswordService.verifyOtp(emailRequest);
        logger.info(LoggerConstants.LOG_OTP_VERIFICATION_RESPONSE, response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePasssword changePasssword) {
        logger.info(LoggerConstants.LOG_CHANGING_PASSWORD, changePasssword.getEmail());
        String response = forgotPasswordService.changePassword(changePasssword);

        if (response.equals("Passwords do not match!")) {
            logger.warn(LoggerConstants.LOG_PASSWORD_CHANGE_FAILED, response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        logger.info(LoggerConstants.LOG_PASSWORD_CHANGED_SUCCESS, changePasssword.getEmail());
        return ResponseEntity.ok(response);
    }
}
