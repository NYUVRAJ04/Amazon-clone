package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationRequest;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.model.InsuranceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceApplicationTest {

    private BuyersEntity buyer;
    private InsuranceApplicationRequest request;

    @BeforeEach
    void setUp() {
        // Create a mock buyer for testing
        buyer = new BuyersEntity();
        buyer.setBuyerId(1L);
        // Set additional properties for the buyer if necessary

        // Create a mock InsuranceApplicationRequest
        request = new InsuranceApplicationRequest();
        request.setInsuranceType("Health");
        request.setCoverageAmount(50000.0);
        request.setAssetValue(100000.0);
        request.setPreviousClaims(false);
    }

    // Test case for the default constructor
    @Test
    void testDefaultConstructor() {
        InsuranceApplication application = new InsuranceApplication();
        assertNull(application.getApplicationId());
        assertNull(application.getBuyer());
        assertNull(application.getInsuranceType());
        assertEquals(0.0, application.getCoverageAmount());
        assertEquals(0.0, application.getAssetValue());
        assertFalse(application.isPreviousClaims());
        assertNull(application.getStatus());
    }

    // Test case for the all-args constructor
    @Test
    void testAllArgsConstructor() {
        InsuranceApplication application = new InsuranceApplication(1L, buyer, "Health", 50000.0, 100000.0, false, "Approved");

        assertEquals(1L, application.getApplicationId());
        assertEquals(buyer, application.getBuyer());
        assertEquals("Health", application.getInsuranceType());
        assertEquals(50000.0, application.getCoverageAmount());
        assertEquals(100000.0, application.getAssetValue());
        assertFalse(application.isPreviousClaims());
        assertEquals("Approved", application.getStatus());
    }

    // Test case for the constructor that takes InsuranceApplicationRequest
    @Test
    void testConstructorWithInsuranceApplicationRequest() {
        InsuranceApplication application = new InsuranceApplication(request, buyer, "Pending");

        assertEquals(buyer, application.getBuyer());
        assertEquals("Health", application.getInsuranceType());
        assertEquals(50000.0, application.getCoverageAmount());
        assertEquals(100000.0, application.getAssetValue());
        assertFalse(application.isPreviousClaims());
        assertEquals("Pending", application.getStatus());
    }
}
