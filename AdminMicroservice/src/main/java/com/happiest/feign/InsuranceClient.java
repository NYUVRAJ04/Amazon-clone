package com.happiest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "http://EligibilityMicroservice/insurance")
public interface InsuranceClient {

    @GetMapping("/count")
    public Long getTotalInsuranceApplications();

}
