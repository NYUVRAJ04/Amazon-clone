package com.happiest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="buyers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Buyers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyerId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email", nullable = false)
    private Users user; // Foreign key reference to User table

    @Column(name = "annual_income", nullable = false)
    private double annualIncome;

    @Column(name = "credit_score", nullable = false)
    private int creditScore;

    @Column(name = "has_loan_defaults", nullable = false)
    private boolean hasLoanDefaults;

    @Column(name = "is_eligible_for_insurance", nullable = false)
    private boolean isEligibleForInsurance;

    @Column(name = "is_eligible_for_loan", nullable = false)
    private boolean isEligibleForLoan;

    @Column(name = "land_area", nullable = false)
    private double landArea;

    @Column(name = "years_in_farming", nullable = false)
    private int yearsInFarming;

    // Method to set default values before persisting
    @PrePersist
    public void setDefaultValues() {
        if (annualIncome <= 0) {
            annualIncome = 0.0; // Default to 0.0
        }
        if (creditScore <= 0) {
            creditScore = 300; // Default credit score
        }
        hasLoanDefaults = false; // Default to false
        isEligibleForInsurance = false; // Default to false
        isEligibleForLoan = false; // Default to false
        if (landArea <= 0) {
            landArea = 0.0; // Default to 0.0
        }
        if (yearsInFarming <= 0) {
            yearsInFarming = 1; // Default to 1 year
        }
    }
}



