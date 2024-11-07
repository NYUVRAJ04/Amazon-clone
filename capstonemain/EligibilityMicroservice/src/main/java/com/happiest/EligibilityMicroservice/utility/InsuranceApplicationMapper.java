package com.happiest.EligibilityMicroservice.utility;

import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationResponse;
import com.happiest.EligibilityMicroservice.model.InsuranceApplication;
import org.springframework.stereotype.Component;

@Component
public class InsuranceApplicationMapper {

    public InsuranceApplicationResponse mapToResponse(InsuranceApplication application) {
        InsuranceApplicationResponse response = new InsuranceApplicationResponse();
        response.setApplicationId(application.getApplicationId());
        response.setBuyerId(application.getBuyer().getBuyerId());
        response.setInsuranceType(application.getInsuranceType());
        response.setCoverageAmount(application.getCoverageAmount());
        response.setAssetValue(application.getAssetValue());
        response.setPreviousClaims(application.isPreviousClaims());
        response.setStatus(application.getStatus());
        return response;
    }
}
