package com.happiest.constants;

public class MessageConstants {
    public static final String CROP_SAVE_ATTEMPT = "Attempting to save crop: {}";
    public static final String CROP_FILE_PROVIDED = "File provided, proceeding to upload for crop: {}";
    public static final String CROP_FILE_UPLOADED = "File uploaded, image URL set to: {}";
    public static final String CROP_SAVE_SUCCESS = "Crop saved successfully with ID: {}";
    public static final String CROP_FETCH_ALL = "Fetching all crops from the repository";
    public static final String CROP_FETCH_ID = "Fetching crop with ID: {}";
    public static final String CROP_NOT_FOUND = "Crop not found with ID: {}";  // Added for not found scenario
    public static final String CROP_DELETE_ID = "Deleting crop with ID: {}";
    public static final String CROP_DELETE_SUCCESS = "Crop deleted successfully with ID: {}";
    public static final String CROP_FETCH_CATEGORIES = "Fetching distinct category names for crops";
    public static final String CROP_UPDATE_ATTEMPT = "Attempting to update crop with ID: {}";
    public static final String CROP_FOUND_UPDATE_FIELDS = "Crop found with ID: {}, updating fields";
    public static final String CROP_UPDATE_NAME = "Updated name to: {}";
    public static final String CROP_UPDATE_DESCRIPTION = "Updated description";
    public static final String CROP_UPDATE_RENTAL_PRICE = "Updated rental price to: {}";
    public static final String CROP_UPDATE_SELLING_PRICE = "Updated selling price to: {}";
    public static final String CROP_UPDATE_AVAILABILITY = "Updated crop availability";
    public static final String CROP_UPDATE_CATEGORY_NAME = "Updated category name to: {}";
    public static final String CROP_NEW_FILE_PROVIDED = "New file provided, uploading for crop with ID: {}";
    public static final String CROP_IMAGE_URL_UPDATED = "Updated image URL to: {}";
    public static final String CROP_UPDATE_SUCCESS = "Crop updated successfully with ID: {}";
    public static final String CROP_SAVE_ERROR = "Error occurred while saving crop: {}"; // Error during crop saving
    public static final String CROP_DELETE_ERROR = "Error occurred while deleting crop with ID: {}"; // Error during crop deletion

    // Logging messages for successful actions
    public static final String CROP_FETCH_SUCCESS = "Successfully fetched crop with ID: {}"; // When fetching is successful
    public static final String CROP_FETCH_ALL_SUCCESS = "Successfully fetched all crops, total: {}"; // Success fetching all crops
}
