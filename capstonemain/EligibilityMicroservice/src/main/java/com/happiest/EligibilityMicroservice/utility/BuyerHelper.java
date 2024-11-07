package com.happiest.EligibilityMicroservice.utility;

import com.happiest.EligibilityMicroservice.dto.BuyerDTO;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuyerHelper {


    public static BuyersEntity updateFromDTO(BuyersEntity buyersEntity, BuyerDTO buyerDTO) {
        buyersEntity.setYearsInFarming(buyerDTO.getYearsInFarming());
        buyersEntity.setAnnualIncome(buyerDTO.getAnnualIncome());
        buyersEntity.setHasLoanDefaults(buyerDTO.isHasLoanDefaults());
        buyersEntity.setLandArea(buyerDTO.getLandArea());
        buyersEntity.setCreditScore(buyerDTO.getCreditScore()); // Assuming this field exists

        return buyersEntity;
    }
}
