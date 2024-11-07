package com.happiest.EligibilityMicroservice.utility;

import com.happiest.EligibilityMicroservice.dto.LoanEligibilityResponseDTO;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanEligibilityHelper {

    /**
     * Checks if a buyer is eligible for a loan based on several criteria.
     * @param buyer The buyer entity containing all necessary fields for eligibility check.
     * @return LoanEligibilityResponseDTO indicating the eligibility status and message.
     */
    public LoanEligibilityResponseDTO checkEligibility(BuyersEntity buyer) {
        LoanEligibilityResponseDTO response = new LoanEligibilityResponseDTO();

        // Check eligibility based on criteria
        if (buyer.getCreditScore() < 600) {
            response.setEligible(false);
            response.setMessage("Loan eligibility failed: Insufficient credit score.");
            response.setBuyerId(buyer.getBuyerId());
        } else if (buyer.getAnnualIncome() < 30000) {
            response.setEligible(false);
            response.setMessage("Loan eligibility failed: Insufficient annual income.");
            response.setBuyerId(buyer.getBuyerId());
        } else if (buyer.getYearsInFarming() < 2) {
            response.setEligible(false);
            response.setMessage("Loan eligibility failed: Insufficient years in farming.");
            response.setBuyerId(buyer.getBuyerId());
        } else if (buyer.getLandArea() < 2.0) {
            response.setEligible(false);
            response.setMessage("Loan eligibility failed: Insufficient land area.");
            response.setBuyerId(buyer.getBuyerId());
        } else if (buyer.isHasLoanDefaults()) {
            response.setEligible(false);
            response.setMessage("Loan eligibility failed: Previous loan defaults.");
            response.setBuyerId(buyer.getBuyerId());
        } else {
            // If all criteria are met
            response.setEligible(true);
            response.setMessage("Loan eligibility confirmed.");
            response.setBuyerId(buyer.getBuyerId());
        }

        return response;
    }
}
