package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationRequest;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.utility.EligibilityEvaluator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EligibilityEvaluatorTest {

    private EligibilityEvaluator eligibilityEvaluator;


    @BeforeEach
    void setUp() {
        eligibilityEvaluator = new EligibilityEvaluator();
    // Correctly assign the request object
    }

    // Test case where eligibility fails due to insufficient years in farming
    @Test
    void testEligibilityFailsDueToLowYearsInFarming() {
        BuyersEntity buyer = createBuyer(3, 20000, 6.0, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertFalse(isEligible, "Eligibility should fail due to insufficient years in farming");
    }

    // Test case where eligibility fails due to low annual income
    @Test
    void testEligibilityFailsDueToLowAnnualIncome() {
        BuyersEntity buyer = createBuyer(6, 9000, 6.0, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertFalse(isEligible, "Eligibility should fail due to low annual income");
    }

    // Test case where eligibility fails due to loan default history
    @Test
    void testEligibilityFailsDueToLoanDefaultHistory() {
        BuyersEntity buyer = createBuyer(6, 20000, 6.0, true);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertFalse(isEligible, "Eligibility should fail due to loan defaults");
    }

    // Test case where eligibility fails due to insufficient land area
    @Test
    void testEligibilityFailsDueToLowLandArea() {
        BuyersEntity buyer = createBuyer(6, 20000, 4.5, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertFalse(isEligible, "Eligibility should fail due to insufficient land area");
    }

    // Test case where all eligibility criteria are met, so eligibility should pass
    @Test
    void testEligibilityPassesAllCriteria() {
        BuyersEntity buyer = createBuyer(6, 20000, 6.0, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertTrue(isEligible, "Eligibility should pass as all criteria are met");
    }

    // Boundary Test Cases

    // Test case where eligibility fails due to years in farming just below the threshold
    @Test
    void testEligibilityFailsAtBoundaryYearsInFarming() {
        BuyersEntity buyer = createBuyer(4, 20000, 6.0, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertFalse(isEligible, "Eligibility should fail with years in farming just below the threshold");
    }

    // Test case where eligibility passes with years in farming at the exact threshold
    @Test
    void testEligibilityPassesAtBoundaryYearsInFarming() {
        BuyersEntity buyer = createBuyer(5, 20000, 6.0, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertTrue(isEligible, "Eligibility should pass with years in farming at the threshold");
    }

    // Test case where eligibility fails due to annual income just below the threshold
    @Test
    void testEligibilityFailsAtBoundaryIncome() {
        BuyersEntity buyer = createBuyer(6, 9999, 6.0, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertFalse(isEligible, "Eligibility should fail with annual income just below the threshold");
    }

    // Test case where eligibility passes with annual income at the threshold
    @Test
    void testEligibilityPassesAtBoundaryIncome() {
        BuyersEntity buyer = createBuyer(6, 10000, 6.0, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertTrue(isEligible, "Eligibility should pass with annual income at the threshold");
    }

    // Test case where eligibility fails due to land area just below the threshold
    @Test
    void testEligibilityFailsAtBoundaryLandArea() {
        BuyersEntity buyer = createBuyer(6, 20000, 4.9, false);
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertFalse(isEligible, "Eligibility should fail with land area just below the threshold");
    }

    // Test case where eligibility passes with land area at the threshold
    @Test
    void testEligibilityPassesAtBoundaryLandArea() {
        BuyersEntity buyer = createBuyer(6, 20000, 5.0, false);
        System.out.println("Testing with land area: " + buyer.getLandArea());
        boolean isEligible = eligibilityEvaluator.evaluateInsuranceEligibility(buyer);

        assertTrue(isEligible, "Eligibility should pass with land area at the threshold");
    }

    // Test case where eligibility fails due to coverage amount greater than or equal to asset value




    // Helper method to create a BuyersEntity with specified properties
    private BuyersEntity createBuyer(int yearsInFarming, double annualIncome, double landArea, boolean hasLoanDefaults) {
        BuyersEntity buyer = new BuyersEntity();
        buyer.setYearsInFarming(yearsInFarming);
        buyer.setAnnualIncome(annualIncome);
        buyer.setLandArea(landArea);
        buyer.setHasLoanDefaults(hasLoanDefaults);
        buyer.setBuyerId(1L); // Set a sample buyer ID for testing
        return buyer;
    }
}
