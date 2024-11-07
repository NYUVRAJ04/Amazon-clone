package com.happiest.service;

import com.happiest.dto.PlatformStatsDTO;
import com.happiest.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService
{

    @Autowired
    private BuyerClient buyerClient;

    @Autowired
    private SellerClient sellerClient;

    @Autowired
    private MachineryClient machineryClient;

    @Autowired
    private LoanClient loanClient;


    @Autowired
    private InsuranceClient insuranceClient;

    // Fetch overall platform stats
    public PlatformStatsDTO getPlatformStats() {
        PlatformStatsDTO stats = new PlatformStatsDTO();

        // Calling the respective microservices to get the counts
        stats.setTotalFarmers(buyerClient.getTotalFarmers());
        stats.setTotalSellers(sellerClient.getTotalSellers());
        stats.setTotalLoanApplications(loanClient.getTotalLoanApplications());
        stats.setTotalInsuranceApplications(insuranceClient.getTotalInsuranceApplications());

        return stats;
    }


}
