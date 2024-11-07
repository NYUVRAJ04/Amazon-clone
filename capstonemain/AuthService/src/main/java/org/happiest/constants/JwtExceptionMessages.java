package org.happiest.constants;

public class JwtExceptionMessages {
    public static final String TOKEN_GENERATION_ERROR = "Error generating token";
    public static final String TOKEN_EXPIRATION_ERROR = "Token has expired";
    public static final String TOKEN_EXTRACTION_ERROR = "Error extracting user email from token";
    public static final String TOKEN_DECODING_ERROR = "Error decoding secret key";
    public static final String CLAIM_EXTRACTION_ERROR = "Error extracting claims from token";
}
