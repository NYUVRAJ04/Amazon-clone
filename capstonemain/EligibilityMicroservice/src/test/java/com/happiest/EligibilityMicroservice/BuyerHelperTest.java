package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.dto.BuyerDTO;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.utility.BuyerHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BuyerHelperTest {

    @Test
    public void testUpdateFromDTO() {
        // Arrange
        BuyersEntity buyersEntity = new BuyersEntity();
        BuyerDTO buyerDTO = new BuyerDTO();
        buyerDTO.setYearsInFarming(10);
        buyerDTO.setAnnualIncome(50000);
        buyerDTO.setHasLoanDefaults(true);
        buyerDTO.setLandArea(100.5);
        buyerDTO.setCreditScore(750);

        // Act
        BuyersEntity updatedEntity = BuyerHelper.updateFromDTO(buyersEntity, buyerDTO);

        // Assert
        assertEquals(10, updatedEntity.getYearsInFarming());
        assertEquals(50000, updatedEntity.getAnnualIncome());
        assertTrue(updatedEntity.isHasLoanDefaults());
        assertEquals(100.5, updatedEntity.getLandArea());
        assertEquals(750, updatedEntity.getCreditScore());
    }

    @Test
    public void testUpdateFromDTOWithNullValues() {
        // Arrange
        BuyersEntity buyersEntity = new BuyersEntity();
        BuyerDTO buyerDTO = new BuyerDTO();
        buyerDTO.setYearsInFarming(0);
        buyerDTO.setAnnualIncome(0);
        buyerDTO.setHasLoanDefaults(false);
        buyerDTO.setLandArea(0.0);
        buyerDTO.setCreditScore(0);

        // Act
        BuyersEntity updatedEntity = BuyerHelper.updateFromDTO(buyersEntity, buyerDTO);

        // Assert
        assertEquals(0, updatedEntity.getYearsInFarming());
        assertEquals(0, updatedEntity.getAnnualIncome());
        assertFalse(updatedEntity.isHasLoanDefaults());
        assertEquals(0.0, updatedEntity.getLandArea());
        assertEquals(0, updatedEntity.getCreditScore());
    }

    @Test
    public void testUpdateFromDTOWithNegativeValues() {
        // Arrange
        BuyersEntity buyersEntity = new BuyersEntity();
        BuyerDTO buyerDTO = new BuyerDTO();
        buyerDTO.setYearsInFarming(-5);
        buyerDTO.setAnnualIncome(-10000);
        buyerDTO.setHasLoanDefaults(false);
        buyerDTO.setLandArea(-50.0);
        buyerDTO.setCreditScore(-300);

        // Act
        BuyersEntity updatedEntity = BuyerHelper.updateFromDTO(buyersEntity, buyerDTO);

        // Assert
        assertEquals(-5, updatedEntity.getYearsInFarming());
        assertEquals(-10000, updatedEntity.getAnnualIncome());
        assertFalse(updatedEntity.isHasLoanDefaults());
        assertEquals(-50.0, updatedEntity.getLandArea());
        assertEquals(-300, updatedEntity.getCreditScore());
    }
}
