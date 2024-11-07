package org.happiest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceApplicationResponse {
    private Long applicationId;
    private Long buyerId;
    private String insuranceType;
    private double coverageAmount;
    private double assetValue;
    private boolean previousClaims;
    private String status;
}
