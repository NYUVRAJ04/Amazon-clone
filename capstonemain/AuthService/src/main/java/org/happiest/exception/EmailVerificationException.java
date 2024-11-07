package org.happiest.exception;

public class EmailVerificationException extends RuntimeException {
    public EmailVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
