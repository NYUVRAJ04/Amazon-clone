package com.happiest.EligibilityMicroservice;

import com.happiest.EligibilityMicroservice.controller.InsuranceApplicationController;
import com.happiest.EligibilityMicroservice.dto.BuyerDTO;
import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationRequest;
import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationResponse;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.service.InsuranceApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InsuranceApplicationController.class)
class InsuranceApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceApplicationService insuranceApplicationService;

    @Test
    void testApplyForInsurance() throws Exception {
        InsuranceApplicationResponse mockResponse = new InsuranceApplicationResponse();
        mockResponse.setStatus("APPROVED");

        // Mock service layer behavior
        Mockito.when(insuranceApplicationService.applyForInsurance(any(InsuranceApplicationRequest.class)))
                .thenReturn(mockResponse);

        // Test the POST /apply endpoint
        mockMvc.perform(post("/insurance/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"buyerId\": 1, \"insuranceType\": \"Health\", \"coverageAmount\": 100000, \"assetValue\": 50000, \"previousClaims\": false }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void testGetApplicationsForBuyer() throws Exception {
        // Mock service behavior
        Mockito.when(insuranceApplicationService.getApplicationsForBuyer(eq(1L)))
                .thenReturn(List.of(new InsuranceApplicationResponse()));

        // Test the GET /buyer/{buyerId}/applications endpoint
        mockMvc.perform(get("/insurance/buyer/1/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testUpdateApplicationStatus() throws Exception {
        InsuranceApplicationResponse mockResponse = new InsuranceApplicationResponse();
        mockResponse.setStatus("UPDATED");

        // Mock service layer behavior
        Mockito.when(insuranceApplicationService.updateApplicationStatus(1L, "UPDATED"))
                .thenReturn(mockResponse);

        // Test the PATCH /application/{applicationId}/status endpoint
        mockMvc.perform(patch("/insurance/application/1/status")
                        .param("status", "UPDATED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UPDATED"));
    }

    @Test
    void testUpdateBuyer() throws Exception {
        BuyersEntity mockBuyer = new BuyersEntity();
        mockBuyer.setBuyerId(1L);
     //   mockBuyer.setName("John Doe Updated");

        BuyerDTO buyerDTO = new BuyerDTO();
       // buyerDTO.setName("John Doe Updated");
        buyerDTO.setEmail("john.doe.updated@example.com");

        // Mock service layer behavior
        Mockito.when(insuranceApplicationService.updateBuyer(eq(1L), any(BuyerDTO.class)))
                .thenReturn(mockBuyer);

        // Test the PUT /updatebuyer/{buyerId} endpoint
        mockMvc.perform(put("/insurance/updatebuyer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john.doe.updated@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyerId").value(1L));
               // .andExpect(jsonPath("$.name").value("John Doe Updated"));
    }

    @Test
    void testGetApplicationStatus() throws Exception {
        InsuranceApplicationResponse mockResponse = new InsuranceApplicationResponse();
        mockResponse.setStatus("APPROVED");

        // Mock service layer behavior
        Mockito.when(insuranceApplicationService.getApplicationById(eq(1L)))
                .thenReturn(mockResponse);

        // Test the GET /application/{applicationId} endpoint
        mockMvc.perform(get("/insurance/application/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void testGetTotalInsuranceApplications() throws Exception {
        // Mock service layer behavior
        Mockito.when(insuranceApplicationService.getTotalInsuranceCount()).thenReturn(5L);

        // Test the GET /count endpoint
        mockMvc.perform(get("/insurance/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testGetTotalInsuranceApplications_ZeroCount() throws Exception {
        // Mock service layer behavior
        Mockito.when(insuranceApplicationService.getTotalInsuranceCount()).thenReturn(0L);

        // Test the GET /count endpoint
        mockMvc.perform(get("/insurance/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}
