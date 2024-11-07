package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationResponse;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.model.InsuranceApplication;
import com.happiest.EligibilityMicroservice.utility.InsuranceApplicationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceApplicationMapperTest {

    private InsuranceApplicationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new InsuranceApplicationMapper();
    }

    // Test case for mapping InsuranceApplication with a valid buyer
    @Test
    void testMapToResponseWithValidBuyer() {
        BuyersEntity buyer = new BuyersEntity();
        buyer.setBuyerId(1L);

        InsuranceApplication application = new InsuranceApplication();
        application.setApplicationId(100L);
        application.setBuyer(buyer);
        application.setInsuranceType("Health");
        application.setCoverageAmount(50000.0);
        application.setAssetValue(100000.0);
        application.setPreviousClaims(false);
        application.setStatus("Approved");

        InsuranceApplicationResponse response = mapper.mapToResponse(application);

        assertEquals(100L, response.getApplicationId());
        assertEquals(1L, response.getBuyerId());
        assertEquals("Health", response.getInsuranceType());
        assertEquals(50000.0, response.getCoverageAmount());
        assertEquals(100000.0, response.getAssetValue());
        assertFalse(response.isPreviousClaims());
        assertEquals("Approved", response.getStatus());
    }

    // Test case for mapping InsuranceApplication with a null buyer
    @Test
    void testMapToResponseWithNullBuyer() {
        InsuranceApplication application = new InsuranceApplication();
        application.setApplicationId(100L);
        application.setBuyer(null); // Set buyer to null
        application.setInsuranceType("Health");
        application.setCoverageAmount(50000.0);
        application.setAssetValue(100000.0);
        application.setPreviousClaims(false);
        application.setStatus("Approved");

        // Since the original method will throw a NullPointerException, we expect this behavior
        Exception exception = assertThrows(NullPointerException.class, () -> {
            mapper.mapToResponse(application);
        });

        String expectedMessage = "Cannot invoke \"com.happiest.EligibilityMicroservice.model.BuyersEntity.getBuyerId()\"";
        String actualMessage = exception.getMessage();

        // Verify that the expected message matches the actual message
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
