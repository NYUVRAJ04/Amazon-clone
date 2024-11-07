package com.happiest.EligibilityMicroservice.controller;

import com.happiest.EligibilityMicroservice.dto.LoanApplicationDTO;
import com.happiest.EligibilityMicroservice.dto.LoanEligibilityResponseDTO;
import com.happiest.EligibilityMicroservice.exception.LoanApplicationException;
import com.happiest.EligibilityMicroservice.service.LoanEligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@CrossOrigin
public class LoanEligibilityController {

    @Autowired
    private LoanEligibilityService loanEligibilityService;

    public LoanEligibilityController(LoanEligibilityService loanEligibilityService) {
        this.loanEligibilityService = loanEligibilityService;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> submitLoanApplication(@RequestBody LoanApplicationDTO loanApplicationDTO) {
        try {
            // Submit the loan application and check eligibility
            LoanEligibilityResponseDTO eligibilityResponse = loanEligibilityService.submitAndCheckLoanApplication(loanApplicationDTO);

            // Return the eligibility response
            if (eligibilityResponse.isEligible()) {
                return ResponseEntity.ok(eligibilityResponse);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eligibilityResponse);
            }
        } catch (LoanApplicationException ex) {
            // Handle specific exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            // Handle unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An unexpected error occurred."));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalLoanApplications() {
        try {
            Long count = loanEligibilityService.getTotalLoansCount();
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Define an ErrorResponse class to standardize error responses
    private static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
