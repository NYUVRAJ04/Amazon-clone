package com.happiest.EligibilityMicroservice.dto;// src/main/java/com/happiest/EligibilityMicroservice/dto/BuyerDTO.java

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BuyerDTO {
    private Long buyerId;
    private String name;
    private String email;
    private Integer creditScore;
    private int yearsInFarming;
    private double annualIncome;
    private boolean hasLoanDefaults;
    private double landArea;
    // Add other fields as necessary
}
