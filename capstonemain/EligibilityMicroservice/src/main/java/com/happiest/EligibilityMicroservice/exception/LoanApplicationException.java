package com.happiest.EligibilityMicroservice.exception;


public class LoanApplicationException extends RuntimeException {
    // Constructor that accepts only the message
    public LoanApplicationException(String message) {
        super(message);
    }

    // Constructor that accepts both the message and the cause
    public LoanApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}