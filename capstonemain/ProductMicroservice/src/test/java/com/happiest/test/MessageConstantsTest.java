package com.happiest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.happiest.constants.MessageConstants;
import org.junit.jupiter.api.Test;

public class MessageConstantsTest {

    @Test
    public void testMessageConstants() {
        assertEquals("Attempting to save crop: {}", MessageConstants.CROP_SAVE_ATTEMPT);
        assertEquals("File provided, proceeding to upload for crop: {}", MessageConstants.CROP_FILE_PROVIDED);
        assertEquals("File uploaded, image URL set to: {}", MessageConstants.CROP_FILE_UPLOADED);
        assertEquals("Crop saved successfully with ID: {}", MessageConstants.CROP_SAVE_SUCCESS);
        assertEquals("Fetching all crops from the repository", MessageConstants.CROP_FETCH_ALL);
        assertEquals("Fetching crop with ID: {}", MessageConstants.CROP_FETCH_ID);
        assertEquals("Crop not found with ID: {}", MessageConstants.CROP_NOT_FOUND);
        assertEquals("Deleting crop with ID: {}", MessageConstants.CROP_DELETE_ID);
        assertEquals("Crop deleted successfully with ID: {}", MessageConstants.CROP_DELETE_SUCCESS);
        assertEquals("Fetching distinct category names for crops", MessageConstants.CROP_FETCH_CATEGORIES);
        assertEquals("Attempting to update crop with ID: {}", MessageConstants.CROP_UPDATE_ATTEMPT);
        assertEquals("Crop found with ID: {}, updating fields", MessageConstants.CROP_FOUND_UPDATE_FIELDS);
        assertEquals("Updated name to: {}", MessageConstants.CROP_UPDATE_NAME);
        assertEquals("Updated description", MessageConstants.CROP_UPDATE_DESCRIPTION);
        assertEquals("Updated rental price to: {}", MessageConstants.CROP_UPDATE_RENTAL_PRICE);
        assertEquals("Updated selling price to: {}", MessageConstants.CROP_UPDATE_SELLING_PRICE);
        assertEquals("Updated crop availability", MessageConstants.CROP_UPDATE_AVAILABILITY);
        assertEquals("Updated category name to: {}", MessageConstants.CROP_UPDATE_CATEGORY_NAME);
        assertEquals("New file provided, uploading for crop with ID: {}", MessageConstants.CROP_NEW_FILE_PROVIDED);
        assertEquals("Updated image URL to: {}", MessageConstants.CROP_IMAGE_URL_UPDATED);
        assertEquals("Crop updated successfully with ID: {}", MessageConstants.CROP_UPDATE_SUCCESS);
        assertEquals("Error occurred while saving crop: {}", MessageConstants.CROP_SAVE_ERROR);
        assertEquals("Error occurred while deleting crop with ID: {}", MessageConstants.CROP_DELETE_ERROR);
        assertEquals("Successfully fetched crop with ID: {}", MessageConstants.CROP_FETCH_SUCCESS);
        assertEquals("Successfully fetched all crops, total: {}", MessageConstants.CROP_FETCH_ALL_SUCCESS);
    }
}
