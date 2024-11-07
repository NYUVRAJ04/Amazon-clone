package com.happiest.EligibilityMicroservice.controller;

import com.happiest.EligibilityMicroservice.dto.BuyerDTO;
import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationRequest;
import com.happiest.EligibilityMicroservice.dto.InsuranceApplicationResponse;
import com.happiest.EligibilityMicroservice.exception.GlobalExceptionHandler;
import com.happiest.EligibilityMicroservice.exception.InsuranceApplicationException;
import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import com.happiest.EligibilityMicroservice.service.InsuranceApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insurance")
@CrossOrigin
public class InsuranceApplicationController {
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Logger logger = LogManager.getLogger(InsuranceApplicationController.class);

    @Autowired
    private InsuranceApplicationService insuranceApplicationService;

    @PostMapping("/apply")
    @Operation(summary = "Apply for Insurance", description = "Submit an insurance application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid application details"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<?> applyForInsurance(
            @Validated @RequestBody InsuranceApplicationRequest request) {
        logger.info("Received insurance application request: {}", request);
        try {
            InsuranceApplicationResponse response = insuranceApplicationService.applyForInsurance(request);
            logger.info("Insurance application response: {}", response);
            return ResponseEntity.ok(response);
        } catch (InsuranceApplicationException ex) {
            errorLogger.error("Error applying for insurance: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        } catch (Exception e) {
            errorLogger.error("Error applying for insurance: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred."));
        }
    }

    @GetMapping("/buyer/{buyerId}/applications")
    @Operation(summary = "Get Applications for Buyer", description = "Retrieve insurance applications for a specific buyer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Applications retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<?> getApplicationsForBuyer(@PathVariable Long buyerId) {
        logger.info("Fetching applications for buyer ID: {}", buyerId);
        try {
            List<InsuranceApplicationResponse> applications = insuranceApplicationService.getApplicationsForBuyer(buyerId);
            logger.info("Applications for buyer ID {}: {}", buyerId, applications);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            errorLogger.error("Error fetching applications for buyer ID {}: {}", buyerId, e);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred."));
        }
    }

    @GetMapping("/application/{applicationId}")
    @Operation(summary = "Get Application Status", description = "Retrieve the status of a specific insurance application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application status retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid application ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<?> getApplicationStatus(@PathVariable Long applicationId) {
        logger.info("Fetching application status for application ID: {}", applicationId);
        try {
            InsuranceApplicationResponse response = insuranceApplicationService.getApplicationById(applicationId);
            logger.info("Application status for application ID {}: {}", applicationId, response);
            return ResponseEntity.ok(response);
        } catch (InsuranceApplicationException ex) {
            errorLogger.error("Error fetching application status for application ID {}: {}", applicationId, ex);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        } catch (Exception e) {
            errorLogger.error("Error fetching application status for application ID {}: {}", applicationId, e);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred."));
        }
    }

    @PatchMapping("/application/{applicationId}/status")
    @Operation(summary = "Update Application Status", description = "Update the status of a specific insurance application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid application ID or status"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long applicationId, @RequestParam String status) {
        logger.info("Updating application status for application ID: {} to {}", applicationId, status);
        try {
            InsuranceApplicationResponse response = insuranceApplicationService.updateApplicationStatus(applicationId, status);
            logger.info("Updated application status for application ID {}: {}", applicationId, response);
            return ResponseEntity.ok(response);
        } catch (InsuranceApplicationException ex) {
            errorLogger.error("Error updating application status for application ID {}: {}", applicationId, ex);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        } catch (Exception e) {
            errorLogger.error("Error updating application status for application ID {}: {}", applicationId, e);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred."));
        }
    }

    @PutMapping("/updatebuyer/{buyerId}")
    @Operation(summary = "Update Buyer Information", description = "Update information for a specific buyer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buyer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid buyer ID or details"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<?> updateBuyer(@PathVariable Long buyerId, @RequestBody BuyerDTO buyerDTO) {
        logger.info("Updating buyer ID: {} with data: {}", buyerId, buyerDTO);
        try {
            BuyersEntity updatedBuyer = insuranceApplicationService.updateBuyer(buyerId, buyerDTO);
            logger.info("Updated buyer ID {}: {}", buyerId, updatedBuyer);
            return ResponseEntity.ok(updatedBuyer);
        } catch (InsuranceApplicationException ex) {
            errorLogger.error("Error updating buyer ID {}: {}", buyerId, ex);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        } catch (Exception e) {
            errorLogger.error("Error updating buyer ID {}: {}", buyerId, e);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred."));
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Get Total Insurance Applications Count", description = "Retrieve the total count of insurance applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total count retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<?> getTotalInsuranceApplications() {
        logger.info("Fetching total insurance applications count");
        try {
            Long count = insuranceApplicationService.getTotalInsuranceCount();
            logger.info("Total insurance applications count: {}", count);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            errorLogger.error("Error fetching total insurance applications count", e);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred."));
        }
    }

    // Define an ErrorResponse class to standardize error responses
    private static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}

