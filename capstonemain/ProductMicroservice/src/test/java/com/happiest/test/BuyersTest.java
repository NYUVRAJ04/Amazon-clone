package com.happiest.test;


import com.happiest.model.Buyers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuyersTest {

    private Buyers buyer;

    @BeforeEach
    void setUp() {
        buyer = new Buyers();
    }

    @Test
    void testDefaultValuesOnPrePersist() {
        buyer.setAnnualIncome(0);
        buyer.setCreditScore(0);
        buyer.setLandArea(0);
        buyer.setYearsInFarming(0);

        buyer.setDefaultValues();

        assertEquals(0.0, buyer.getAnnualIncome(), "Annual income should default to 0.0");
        assertEquals(300, buyer.getCreditScore(), "Credit score should default to 300");
        assertFalse(buyer.isHasLoanDefaults(), "Has loan defaults should default to false");
        assertFalse(buyer.isEligibleForInsurance(), "Is eligible for insurance should default to false");
        assertFalse(buyer.isEligibleForLoan(), "Is eligible for loan should default to false");
        assertEquals(0.0, buyer.getLandArea(), "Land area should default to 0.0");
        assertEquals(1, buyer.getYearsInFarming(), "Years in farming should default to 1 year");
    }

    @Test
    void testPositiveValuesRemainUnchanged() {
        buyer.setAnnualIncome(50000);
        buyer.setCreditScore(750);
        buyer.setLandArea(5.0);
        buyer.setYearsInFarming(10);

        buyer.setDefaultValues();

        assertEquals(50000, buyer.getAnnualIncome(), "Annual income should remain unchanged");
        assertEquals(750, buyer.getCreditScore(), "Credit score should remain unchanged");
        assertEquals(5.0, buyer.getLandArea(), "Land area should remain unchanged");
        assertEquals(10, buyer.getYearsInFarming(), "Years in farming should remain unchanged");
    }
}
