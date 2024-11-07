package com.happiest.EligibilityMicroservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "loan_applications")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private BuyersEntity buyer;

    @NotNull
    private BigDecimal amountRequested;

    @NotNull
    private String purpose;

    @NotNull
    private String incomeVerification;


    private Integer creditScore;


    private BigDecimal debtToIncomeRatio;

    private String collateral;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        REJECTED,
        APPROVED,
        DENIED
    }

}
