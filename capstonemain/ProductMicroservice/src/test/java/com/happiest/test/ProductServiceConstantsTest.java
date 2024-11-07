package com.happiest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.happiest.constants.ProductServiceConstants;
import org.junit.jupiter.api.Test;

public class ProductServiceConstantsTest {

    @Test
    public void testProductServiceConstants() {
        // Testing product category constants
        assertEquals("machinery", ProductServiceConstants.CATEGORY_MACHINERY);
        assertEquals("crop", ProductServiceConstants.CATEGORY_CROP);

        // Testing log message constants
        assertEquals("Searching products with term: {}", ProductServiceConstants.LOG_SEARCHING_PRODUCTS);
        assertEquals("Found {} machineries and {} crops matching search term", ProductServiceConstants.LOG_FOUND_PRODUCTS);
        assertEquals("Retrieving all products", ProductServiceConstants.LOG_RETRIEVING_ALL_PRODUCTS);
        assertEquals("Found {} machineries and {} crops", ProductServiceConstants.LOG_FOUND_ALL_PRODUCTS);
        assertEquals("Adding new product with category: {}", ProductServiceConstants.LOG_ADDING_NEW_PRODUCT);
        assertEquals("{} product saved with ID: {}", ProductServiceConstants.LOG_PRODUCT_SAVED);
        assertEquals("Retrieving distinct category names for category: {}", ProductServiceConstants.LOG_RETRIEVING_DISTINCT_CATEGORY_NAMES);
        assertEquals("Found {} distinct categories", ProductServiceConstants.LOG_FOUND_DISTINCT_CATEGORIES);
        assertEquals("Updating products with category name: {}", ProductServiceConstants.LOG_UPDATING_PRODUCTS);
        assertEquals("No {} found with category name: {}", ProductServiceConstants.LOG_NO_PRODUCT_FOUND);
        assertEquals("Invalid category: {}", ProductServiceConstants.LOG_INVALID_CATEGORY);
        assertEquals("Product Updated", ProductServiceConstants.LOG_PRODUCTS_UPDATED);
    }
}
