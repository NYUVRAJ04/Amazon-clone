package org.happiest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationDTO {
    private Long buyerId; // ID of the buyer
    private BigDecimal amountRequested; // Amount requested for the loan
    private String purpose; // Purpose of the loan
    private String incomeVerification; // Income verification document
    private Integer creditScore; // Credit score of the buyer
    private BigDecimal debtToIncomeRatio; // Debt-to-income ratio
    private String collateral; // Collateral offered for the loan
    private double annualIncome;
}
