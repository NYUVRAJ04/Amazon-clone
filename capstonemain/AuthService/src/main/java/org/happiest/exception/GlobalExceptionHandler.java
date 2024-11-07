package org.happiest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SellerServiceException.class)
    public ResponseEntity<String> handleSellerServiceException(SellerServiceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex)
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> handleTokenExpiredException(TokenExpiredException ex)
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Token Expired: " + ex.getMessage());
    }


    @ExceptionHandler(ExpiredOtpException.class)
    public ResponseEntity<String> handleExpiredOtpException(ExpiredOtpException ex)
    {
        return ResponseEntity.status(HttpStatus.GONE)
                .body("OTP Expired: " + ex.getMessage());
    }

    @ExceptionHandler(EmailVerificationException.class)
    public ResponseEntity<String> handleEmailVerificationException(EmailVerificationException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Email Verification Error: " + ex.getMessage());
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<String> handleEmailSendingException(EmailSendingException ex)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Email Sending Error: " + ex.getMessage());
    }

    @ExceptionHandler(BuyerNotFoundException.class)
    public ResponseEntity<String> handleBuyerNotFoundException(BuyerNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Buyer Not Found: " + ex.getMessage());
    }

    // General exception handler for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<String> handleUserServiceException(UserServiceException e)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}