package com.happiest.constants;

public class Constants {

    // Log Messages for Machinery
    public static final String LOG_RETRIEVING_ALL_MACHINERIES = "All Machineries";
    public static final String LOG_SAVING_MACHINERY = "Saving machinery: {}";
    public static final String LOG_IMAGE_URL_SET = "Image URL set for machinery: {}";
    public static final String LOG_MACHINERY_SAVED_SUCCESSFULLY = "Machinery saved successfully with ID: {}";
    public static final String LOG_RETRIEVING_MACHINERY = "Retrieving machinery with ID: {}";
    public static final String LOG_TOTAL_MACHINERY_RECORDS = "Total machinery records found: {}";
    public static final String LOG_DELETING_MACHINERY = "Deleting machinery with ID: {}";
    public static final String LOG_MACHINERY_DELETED_SUCCESSFULLY = "Machinery with ID: {} deleted successfully";
    public static final String LOG_UPDATING_MACHINERY = "Updating machinery with ID: {}";
    public static final String LOG_CATEGORY_NAMES_RETRIEVED = "Distinct category names retrieved: {}";

    // Log Messages for OrderService
    public static final String LOG_CREATING_ORDER = "Creating new order with details: {}";
    public static final String LOG_ORDER_CREATED_SUCCESS = "Order created successfully with ID: {}";
    public static final String LOG_RETRIEVING_ALL_ORDERS = "Retrieving all orders";
    public static final String LOG_TOTAL_ORDERS_FOUND = "Total orders found: {}";
    public static final String LOG_RETRIEVING_ORDER_BY_ID = "Retrieving order with ID: {}";
    public static final String LOG_ORDER_FOUND = "Order found with ID: {}";
    public static final String LOG_ORDER_NOT_FOUND = "Order not found with ID: {}";
    public static final String LOG_DELETING_ORDER = "Deleting order with ID: {}";
    public static final String LOG_ORDER_DELETED_SUCCESS = "Order with ID: {} deleted successfully";

    // Log Messages for ProductController
    public static final String LOG_RECEIVED_ADD_PRODUCT = "Received request to add product: {}";
    public static final String LOG_PRODUCT_ADDED_SUCCESSFULLY = "Product added successfully: {}";
    public static final String LOG_FETCHING_ALL_PRODUCTS = "Fetching all products.";
    public static final String LOG_TOTAL_PRODUCTS_FETCHED = "Fetched {} products.";
    public static final String LOG_SEARCHING_PRODUCTS = "Searching products with term: {}";
    public static final String LOG_FOUND_PRODUCTS_COUNT = "Found {} products matching the search term.";
    public static final String LOG_FETCHING_DISTINCT_CATEGORY_NAMES = "Fetching distinct category names with category filter: {}";
    public static final String LOG_FETCHED_DISTINCT_CATEGORY_NAMES = "Fetched {} distinct category names.";
    public static final String LOG_RECEIVED_UPDATE_PRODUCT = "Received request to update product: {}";
    public static final String LOG_PRODUCT_UPDATED_SUCCESSFULLY = "Product updated successfully: {}";

    // Error Messages
    public static final String ERROR_MACHINERY_NOT_FOUND = "Machinery not found with ID: ";
    public static final String ERROR_ORDER_NOT_FOUND = "Order not found with ID: ";
    public static final String ERROR_PROCESSING_PRODUCT_DATA = "Error processing the product data: {}";

    private Constants() {
        // Prevent instantiation
    }
}
