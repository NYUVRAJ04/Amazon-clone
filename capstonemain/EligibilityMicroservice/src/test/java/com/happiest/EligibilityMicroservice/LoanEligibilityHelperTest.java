package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.dto.LoanEligibilityResponseDTO;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.utility.LoanEligibilityHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanEligibilityHelperTest {

    private LoanEligibilityHelper loanEligibilityHelper;

    @BeforeEach
    void setUp() {
        loanEligibilityHelper = new LoanEligibilityHelper();
    }

    // Test LoanEligibilityHelper constructor
    @Test
    void testLoanEligibilityHelperConstructor() {
        assertNotNull(loanEligibilityHelper, "LoanEligibilityHelper instance should be created");
    }

    // Test case where eligibility fails due to low credit score
    @Test
    void testEligibilityFailsDueToLowCreditScore() {
        BuyersEntity buyer = createBuyer(500, 40000, 3, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Insufficient credit score.", response.getMessage());
        assertEquals(buyer.getBuyerId(), response.getBuyerId());
    }

    // Test case where eligibility fails due to low annual income
    @Test
    void testEligibilityFailsDueToLowAnnualIncome() {
        BuyersEntity buyer = createBuyer(700, 25000, 3, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Insufficient annual income.", response.getMessage());
        assertEquals(buyer.getBuyerId(), response.getBuyerId());
    }

    // Test case where eligibility fails due to insufficient years in farming
    @Test
    void testEligibilityFailsDueToLowYearsInFarming() {
        BuyersEntity buyer = createBuyer(700, 40000, 1, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Insufficient years in farming.", response.getMessage());
        assertEquals(buyer.getBuyerId(), response.getBuyerId());
    }

    // Test case where eligibility fails due to insufficient land area
    @Test
    void testEligibilityFailsDueToLowLandArea() {
        BuyersEntity buyer = createBuyer(700, 40000, 3, 1.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Insufficient land area.", response.getMessage());
        assertEquals(buyer.getBuyerId(), response.getBuyerId());
    }

    // Test case where eligibility fails due to loan default history
    @Test
    void testEligibilityFailsDueToLoanDefaultHistory() {
        BuyersEntity buyer = createBuyer(700, 40000, 3, 3.0, true);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Previous loan defaults.", response.getMessage());
        assertEquals(buyer.getBuyerId(), response.getBuyerId());
    }

    // Test case where all criteria are met, so eligibility should pass
    @Test
    void testEligibilityPassesAllCriteria() {
        BuyersEntity buyer = createBuyer(700, 40000, 3, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertTrue(response.isEligible());
        assertEquals("Loan eligibility confirmed.", response.getMessage());
        assertEquals(buyer.getBuyerId(), response.getBuyerId());
    }

    // Boundary Test Cases

    // Test case where eligibility fails due to credit score being just below the threshold
    @Test
    void testEligibilityFailedDueToBoundaryCreditScore() {
        BuyersEntity buyer = createBuyer(599, 50000, 5, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Insufficient credit score.", response.getMessage());
    }

    // Test case where eligibility passes with credit score at the boundary
    @Test
    void testEligibilityPassesWithBoundaryCreditScore() {
        BuyersEntity buyer = createBuyer(600, 40000, 5, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertTrue(response.isEligible());
        assertEquals("Loan eligibility confirmed.", response.getMessage());
    }

    // Test case where eligibility fails due to income being just below the threshold
    @Test
    void testEligibilityFailedDueToBoundaryIncome() {
        BuyersEntity buyer = createBuyer(650, 29999, 5, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Insufficient annual income.", response.getMessage());
    }

    // Test case where eligibility passes with income at the threshold
    @Test
    void testEligibilityPassesWithBoundaryIncome() {
        BuyersEntity buyer = createBuyer(650, 30000, 5, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertTrue(response.isEligible());
        assertEquals("Loan eligibility confirmed.", response.getMessage());
    }

    // Test case where eligibility fails due to years in farming being just below the threshold
    @Test
    void testEligibilityFailedDueToBoundaryYearsInFarming() {
        BuyersEntity buyer = createBuyer(650, 40000, 1, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Insufficient years in farming.", response.getMessage());
    }

    // Test case where eligibility passes with years in farming at the threshold
    @Test
    void testEligibilityPassesWithBoundaryYearsInFarming() {
        BuyersEntity buyer = createBuyer(650, 40000, 2, 3.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertTrue(response.isEligible());
        assertEquals("Loan eligibility confirmed.", response.getMessage());
    }

    // Test case where eligibility fails due to land area being just below the threshold
    @Test
    void testEligibilityFailedDueToBoundaryLandArea() {
        BuyersEntity buyer = createBuyer(650, 40000, 5, 1.9, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertFalse(response.isEligible());
        assertEquals("Loan eligibility failed: Insufficient land area.", response.getMessage());
    }

    // Test case where eligibility passes with land area at the threshold
    @Test
    void testEligibilityPassesWithBoundaryLandArea() {
        BuyersEntity buyer = createBuyer(650, 40000, 5, 2.0, false);
        LoanEligibilityResponseDTO response = loanEligibilityHelper.checkEligibility(buyer);

        assertTrue(response.isEligible());
        assertEquals("Loan eligibility confirmed.", response.getMessage());
    }

    // Helper method to create a BuyersEntity with specified properties
    private BuyersEntity createBuyer(int creditScore, double annualIncome, int yearsInFarming, double landArea, boolean hasLoanDefaults) {
        BuyersEntity buyer = new BuyersEntity();
        buyer.setCreditScore(creditScore);
        buyer.setAnnualIncome(annualIncome);
        buyer.setYearsInFarming(yearsInFarming);
        buyer.setLandArea(landArea);
        buyer.setHasLoanDefaults(hasLoanDefaults);
        buyer.setBuyerId(1L); // Set a sample buyer ID
        return buyer;
    }
}
