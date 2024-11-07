package com.happiest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "http://EligibilityMicroservice/loan")
public interface LoanClient
{
    @GetMapping("/count")
    public Long getTotalLoanApplications();




}
