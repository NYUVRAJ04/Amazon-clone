package org.happiest.apigateway;

import org.happiest.dto.LoanApplicationDTO;
import org.happiest.dto.LoanEligibilityResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="http://EligibilityMicroservice/loan")
public interface LoanEligibilityInterface
{
    @PostMapping("/apply")
    public ResponseEntity<LoanEligibilityResponseDTO> submitLoanApplication(
            @RequestBody LoanApplicationDTO loanApplicationDTO);
}
