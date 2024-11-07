package org.happiest.constants;

public class UserServiceExceptionMessages {
    public static final String EMAIL_ALREADY_REGISTERED = "Error during registration: Email is already registered";
    public static final String ADMIN_EXISTS = "Admin already exists with the role: admin. Registration skipped.";
    public static final String BUYER_NOT_FOUND = "Buyer entity not found for the user.";
    public static final String SELLER_NOT_FOUND = "Seller entity not found for the user.";
    public static final String INVALID_PASSWORD = "Authentication failed: Invalid password";
    public static final String USER_NOT_FOUND = "Authentication failed: User not found";
}
