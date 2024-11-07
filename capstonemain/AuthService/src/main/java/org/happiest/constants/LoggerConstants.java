package org.happiest.constants;

public class LoggerConstants {
    public static final String LOG_REGISTERING_USER = "Registering user: {}";
    public static final String LOG_USER_LOGIN_ATTEMPT = "User attempting to log in: {}";
    public static final String LOG_SELLER_ENDPOINT_ACCESSED = "Seller endpoint accessed.";
    public static final String LOG_BUYER_ENDPOINT_ACCESSED = "Buyer endpoint accessed.";
    public static final String LOG_FILE_UPLOAD_TITLE = "Uploading file with title: {}";
    public static final String LOG_FETCHING_PLATFORM_STATS = "Fetching platform stats.";
    public static final String LOG_DELETING_USER = "Request to delete user with email: {}";
    public static final String LOG_INSURANCE_APPLICATION = "Applying for insurance with request: {}";
    public static final String LOG_UPDATING_BUYER = "Updating buyer with ID: {}";
    public static final String LOG_LOAN_APPLICATION_SUBMISSION = "Submitting loan application: {}";
    public static final String LOG_FETCHING_ALL_MACHINERY = "Fetching all machinery.";
    public static final String LOG_FETCHING_ALL_CROPS = "Fetching all crops.";
    public static final String LOG_FETCHING_ALL_PRODUCTS = "Fetching all products.";
    public static final String LOG_SEARCHING_PRODUCTS = "Searching products with term: {}";
    public static final String LOG_CREATING_ORDER = "Creating order: {}";
    public static final String LOG_ADDING_PRODUCT = "Adding product: {}";

    // New constants for SellerController
    public static final String LOG_FETCHING_TOTAL_SELLERS = "Fetching total number of sellers.";
    public static final String LOG_TOTAL_SELLERS_COUNT = "Total sellers count: {}";
    public static final String LOG_FETCHING_ALL_SELLERS = "Fetching all sellers.";
    public static final String LOG_TOTAL_SELLERS_RETRIEVED = "Total sellers retrieved: {}";

    // New constants for ForgotPasswordController
    public static final String LOG_VERIFYING_EMAIL = "Verifying email: {}";
    public static final String LOG_EMAIL_VERIFICATION_RESPONSE = "Email verification response: {}";
    public static final String LOG_VERIFYING_OTP = "Verifying OTP for email: {}";
    public static final String LOG_OTP_VERIFICATION_RESPONSE = "OTP verification response: {}";
    public static final String LOG_CHANGING_PASSWORD = "Changing password for user with email: {}";
    public static final String LOG_PASSWORD_CHANGE_FAILED = "Password change failed: {}";
    public static final String LOG_PASSWORD_CHANGED_SUCCESS = "Password changed successfully for user: {}";

    // New constants for BuyerController
    public static final String LOG_FETCHING_BUYER_BY_ID = "Fetching buyer with ID: {}";
    public static final String LOG_BUYER_FOUND = "Buyer found: {}";
    public static final String LOG_BUYER_NOT_FOUND = "Buyer not found with ID: {}";
    public static final String LOG_TOTAL_BUYERS = "Total number of buyers: {}";
    public static final String LOG_TOTAL_SELLERS = "Total number of sellers: {}";
    public static final String LOG_FETCHING_ALL_BUYERS = "Fetching all buyers";
    public static final String LOG_TOTAL_BUYERS_RETRIEVED = "Total buyers retrieved: {}";

    // New constants for AwarenessController
    public static final String LOG_FETCHING_ALL_AWARENESS_CONTENT = "Fetching all awareness content";
    public static final String LOG_RETURNING_AWARENESS_CONTENT_COUNT = "Returning {} items";
    public static final String LOG_FETCHING_AWARENESS_CONTENT_BY_ID = "Fetching awareness content with ID: {}";
    public static final String LOG_CONTENT_FOUND = "Content found: {}";
    public static final String LOG_NO_CONTENT_FOUND = "No content found with ID: {}";
    public static final String LOG_FETCHING_AWARENESS_CONTENT_BY_CATEGORY = "Fetching awareness content for category ID: {}";
    public static final String LOG_RETURNING_AWARENESS_CONTENT_FOR_CATEGORY_COUNT = "Returning {} items for category ID: {}";
    public static final String LOG_CREATING_NEW_AWARENESS_CONTENT = "Creating new awareness content: {}";
    public static final String LOG_CREATED_AWARENESS_CONTENT_ID = "Created content with ID: {}";
    public static final String LOG_UPDATING_AWARENESS_CONTENT = "Updating awareness content with ID: {}";
    public static final String LOG_UPDATED_AWARENESS_CONTENT_ID = "Updated content with ID: {}";
    public static final String LOG_DELETING_AWARENESS_CONTENT = "Deleting awareness content with ID: {}";
    public static final String LOG_DELETED_AWARENESS_CONTENT_ID = "Deleted content with ID: {}";

}
