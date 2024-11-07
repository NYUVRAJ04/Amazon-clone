package org.happiest.exception;

public class SellerServiceException extends RuntimeException {
    public SellerServiceException(String message) {
        super(message);
    }

    public SellerServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
