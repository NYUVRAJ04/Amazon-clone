package com.happiest.EligibilityMicroservice.exception;

public class BuyerNotFoundException extends RuntimeException {
    public BuyerNotFoundException(String message) {
        super(message);
    }
}