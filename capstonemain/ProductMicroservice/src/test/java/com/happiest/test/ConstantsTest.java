package com.happiest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.happiest.constants.Constants;
import org.junit.jupiter.api.Test;

public class ConstantsTest {

    @Test
    public void testLogMessagesForMachinery() {
        assertEquals("All Machineries", Constants.LOG_RETRIEVING_ALL_MACHINERIES);
        assertEquals("Saving machinery: {}", Constants.LOG_SAVING_MACHINERY);
        assertEquals("Image URL set for machinery: {}", Constants.LOG_IMAGE_URL_SET);
        assertEquals("Machinery saved successfully with ID: {}", Constants.LOG_MACHINERY_SAVED_SUCCESSFULLY);
        assertEquals("Retrieving machinery with ID: {}", Constants.LOG_RETRIEVING_MACHINERY);
        assertEquals("Total machinery records found: {}", Constants.LOG_TOTAL_MACHINERY_RECORDS);
        assertEquals("Deleting machinery with ID: {}", Constants.LOG_DELETING_MACHINERY);
        assertEquals("Machinery with ID: {} deleted successfully", Constants.LOG_MACHINERY_DELETED_SUCCESSFULLY);
        assertEquals("Updating machinery with ID: {}", Constants.LOG_UPDATING_MACHINERY);
        assertEquals("Distinct category names retrieved: {}", Constants.LOG_CATEGORY_NAMES_RETRIEVED);
    }

    @Test
    public void testLogMessagesForOrderService() {
        assertEquals("Creating new order with details: {}", Constants.LOG_CREATING_ORDER);
        assertEquals("Order created successfully with ID: {}", Constants.LOG_ORDER_CREATED_SUCCESS);
        assertEquals("Retrieving all orders", Constants.LOG_RETRIEVING_ALL_ORDERS);
        assertEquals("Total orders found: {}", Constants.LOG_TOTAL_ORDERS_FOUND);
        assertEquals("Retrieving order with ID: {}", Constants.LOG_RETRIEVING_ORDER_BY_ID);
        assertEquals("Order found with ID: {}", Constants.LOG_ORDER_FOUND);
        assertEquals("Order not found with ID: {}", Constants.LOG_ORDER_NOT_FOUND);
        assertEquals("Deleting order with ID: {}", Constants.LOG_DELETING_ORDER);
        assertEquals("Order with ID: {} deleted successfully", Constants.LOG_ORDER_DELETED_SUCCESS);
    }

    @Test
    public void testLogMessagesForProductController() {
        assertEquals("Received request to add product: {}", Constants.LOG_RECEIVED_ADD_PRODUCT);
        assertEquals("Product added successfully: {}", Constants.LOG_PRODUCT_ADDED_SUCCESSFULLY);
        assertEquals("Fetching all products.", Constants.LOG_FETCHING_ALL_PRODUCTS);
        assertEquals("Fetched {} products.", Constants.LOG_TOTAL_PRODUCTS_FETCHED);
        assertEquals("Searching products with term: {}", Constants.LOG_SEARCHING_PRODUCTS);
        assertEquals("Found {} products matching the search term.", Constants.LOG_FOUND_PRODUCTS_COUNT);
        assertEquals("Fetching distinct category names with category filter: {}", Constants.LOG_FETCHING_DISTINCT_CATEGORY_NAMES);
        assertEquals("Fetched {} distinct category names.", Constants.LOG_FETCHED_DISTINCT_CATEGORY_NAMES);
        assertEquals("Received request to update product: {}", Constants.LOG_RECEIVED_UPDATE_PRODUCT);
        assertEquals("Product updated successfully: {}", Constants.LOG_PRODUCT_UPDATED_SUCCESSFULLY);
    }

    @Test
    public void testErrorMessages() {
        assertEquals("Machinery not found with ID: ", Constants.ERROR_MACHINERY_NOT_FOUND);
        assertEquals("Order not found with ID: ", Constants.ERROR_ORDER_NOT_FOUND);
        assertEquals("Error processing the product data: {}", Constants.ERROR_PROCESSING_PRODUCT_DATA);
    }
}
