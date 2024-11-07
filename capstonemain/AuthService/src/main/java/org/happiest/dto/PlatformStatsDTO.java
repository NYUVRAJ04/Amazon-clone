package org.happiest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlatformStatsDTO {

    private Long totalFarmers;
    private Long totalSellers;
    private Long totalMachineries;
    private Long totalLoanApplications;
    private Long totalInsuranceApplications;

}
