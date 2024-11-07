package org.happiest.constants;

public class ServiceLoggerConstants {

    // BuyerService Logging Constants
    public static final String ATTEMPTING_TO_FETCH_ALL_BUYERS = "Attempting to fetch all buyers.";
    public static final String DATABASE_ERROR_FETCHING_ALL_BUYERS = "Database error occurred while fetching all buyers.";
    
    public static final String ATTEMPTING_TO_FETCH_BUYER_BY_ID = "Attempting to fetch buyer by ID: {}";
    public static final String DATABASE_ERROR_FETCHING_BUYER_BY_ID = "Database error occurred while fetching buyer by ID.";
    
    public static final String ATTEMPTING_TO_GET_TOTAL_FARMERS = "Attempting to get total farmers.";
    public static final String DATABASE_ERROR_RETRIEVING_FARMER_COUNT = "Database error occurred while retrieving farmer count.";
    
    // EmailService Logging Constants
    public static final String EMAIL_SENDING_SUCCESS = "Email sent successfully to {}";
    public static final String EMAIL_SENDING_FAILURE = "Failed to send email to {}: {}";

    // Other service logging constants can be added here as needed.
}
