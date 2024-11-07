package com.happiest.EligibilityMicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanEligibilityResponseDTO {
    private boolean eligible; // Indicates if the buyer is eligible for the loan
    private String message; // Message detailing the eligibility result
    private Long buyerId; // ID of the buyer
}
