package com.happiest.EligibilityMicroservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.EligibilityMicroservice.controller.LoanEligibilityController;
import com.happiest.EligibilityMicroservice.dto.LoanApplicationDTO;
import com.happiest.EligibilityMicroservice.dto.LoanEligibilityResponseDTO;
import com.happiest.EligibilityMicroservice.service.LoanEligibilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanEligibilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LoanEligibilityService loanEligibilityService;

    @InjectMocks
    private LoanEligibilityController loanEligibilityController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(loanEligibilityController).build();
    }

    @Test
    public void testSubmitLoanApplicationEligible() throws Exception {
        // Arrange
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setAmountRequested(BigDecimal.valueOf(5000));
        loanApplicationDTO.setBuyerId(1L);
        loanApplicationDTO.setPurpose("Farming");

        LoanEligibilityResponseDTO eligibilityResponse = new LoanEligibilityResponseDTO();
        eligibilityResponse.setEligible(true); // Set the expected response

        // Mocking the service method
        when(loanEligibilityService.submitAndCheckLoanApplication(any())).thenReturn(eligibilityResponse);

        // Act and Assert
        mockMvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loanApplicationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eligible").value(true)); // Check if the response indicates eligibility
    }

    @Test
    public void testSubmitLoanApplicationNotEligible() throws Exception {
        // Arrange
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setAmountRequested(BigDecimal.valueOf(5000));
        loanApplicationDTO.setBuyerId(1L);
        loanApplicationDTO.setPurpose("Farming");

        LoanEligibilityResponseDTO eligibilityResponse = new LoanEligibilityResponseDTO();
        eligibilityResponse.setEligible(false); // Set the expected response

        // Mocking the service method
        when(loanEligibilityService.submitAndCheckLoanApplication(any())).thenReturn(eligibilityResponse);

        // Act and Assert
        mockMvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loanApplicationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eligible").value(false)); // Check if the response indicates not eligible
    }

    @Test
    public void testGetTotalLoanApplications() throws Exception {
        // Arrange
        Long totalLoanApplications = 10L;
        when(loanEligibilityService.getTotalLoansCount()).thenReturn(totalLoanApplications);

        // Act and Assert
        mockMvc.perform(get("/loan/count"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assertEquals("10", content); // Check if the total count is correct
                });
    }
}
