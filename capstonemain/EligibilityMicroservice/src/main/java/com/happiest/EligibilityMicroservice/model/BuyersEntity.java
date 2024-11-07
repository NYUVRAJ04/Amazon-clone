package com.happiest.EligibilityMicroservice.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "buyers")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Getter
@Setter
public class BuyersEntity {

    @Id
    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "is_eligible_for_insurance", nullable = false)
    private boolean isEligibleForInsurance;

    @Column(name = "is_eligible_for_loan", nullable = false)
    private boolean isEligibleForLoan;

    @Column(name = "years_in_farming", nullable = false)
    private int yearsInFarming;

    @Column(name = "annual_income", nullable = false)
    private double annualIncome;

    @Column(name = "has_loan_defaults", nullable = false)
    private boolean hasLoanDefaults;

    @Column(name = "land_area", nullable = false)
    private double landArea;

    @Column(name = "credit_score", nullable = false)
    private int creditScore; // New field

}