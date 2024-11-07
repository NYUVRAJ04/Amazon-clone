package com.happiest.EligibilityMicroservice.utility;

import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationRequest;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EligibilityEvaluator {


    private static final int MIN_YEARS_IN_FARMING = 5;
    private static final double MIN_INCOME_THRESHOLD = 10000.0;
    private static final double MIN_LAND_AREA = 5.0; // in acres

    public boolean evaluateInsuranceEligibility(BuyersEntity buyer) {
        if (buyer.getYearsInFarming() < MIN_YEARS_IN_FARMING) {
            System.out.println("Buyer has less than 5 years in farming: " + buyer.getYearsInFarming());
            return false;
        }

        if (buyer.getAnnualIncome() < MIN_INCOME_THRESHOLD) {
            System.out.println("Buyer has annual income less than $10,000: " + buyer.getAnnualIncome());
            return false;
        }

        if (buyer.isHasLoanDefaults()) {
            System.out.println("Buyer has loan defaults.");
            return false;
        }

        if (buyer.getLandArea() < MIN_LAND_AREA) {
            System.out.println("Buyer has less than 5 acres of land: " + buyer.getLandArea());
            return false;
        }





        return true;
    }
}
