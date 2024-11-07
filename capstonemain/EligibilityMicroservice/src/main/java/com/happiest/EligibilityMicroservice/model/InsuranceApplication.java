// src/main/java/com/happiest/EligibilityMicroservice/model/InsuranceApplication.java

package com.happiest.EligibilityMicroservice.model;

import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insurance_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private BuyersEntity buyer; // Reference to BuyersEntity

    @Column(name = "insurance_type", nullable = false)
    private String insuranceType;

    @Column(name = "coverage_amount", nullable = false)
    private double coverageAmount;

    @Column(name = "asset_value", nullable = false)
    private double assetValue;

    @Column(name = "previous_claims", nullable = false)
    private boolean previousClaims;

    @Column(name = "status", nullable = false)
    private String status; // e.g., APPROVED, PENDING, REJECTED


    public InsuranceApplication(InsuranceApplicationRequest request, BuyersEntity buyer, String status) {
        this.buyer = buyer;
        this.insuranceType = request.getInsuranceType();
        this.coverageAmount = request.getCoverageAmount();
        this.assetValue = request.getAssetValue();
        this.previousClaims = request.getPreviousClaims();
        this.status = status;
    }
}
