package com.happiest.EligibilityMicroservice.exception;

public class InsuranceApplicationException extends RuntimeException {

    public InsuranceApplicationException(String message) {
        super(message);
    }

    public InsuranceApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
