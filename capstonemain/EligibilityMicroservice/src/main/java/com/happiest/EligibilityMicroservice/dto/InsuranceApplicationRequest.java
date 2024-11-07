package com.happiest.EligibilityMicroservice.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Getter
@Setter
public class InsuranceApplicationRequest {

    @NotNull
    private Long buyerId;

    @NotBlank
    private String insuranceType;


    private double coverageAmount;


    private double assetValue;

    @NotNull
    private Boolean previousClaims;


}
